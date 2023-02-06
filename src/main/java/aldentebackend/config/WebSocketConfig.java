package aldentebackend.config;

import aldentebackend.security.JwtUtil;
import aldentebackend.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");

        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/notifications").setAllowedOrigins("http://localhost:4200");
        registry.addEndpoint("/notifications").setAllowedOrigins("http://localhost:4200").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                     ; // access authentication header(s)
                    System.out.println("accessor = " + accessor);


                    String username = null;

                    if (accessor.getNativeHeader("Authorization") == null) {
                        return message;
                    }
                    String jwt = accessor.getNativeHeader("Authorization").get(0).replace("Bearer ", "");
                    try {
                        username = jwtUtil.extractUsernameFromToken(jwt);
                    } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException ignored) {
                    }

                    if (username != null) {
                        UserDetails userDetails;
                        try {
                            userDetails = userService.loadUserByUsername(username);

                            if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt, userDetails))) {
                                var usernamePasswordAuthenticationToken = jwtUtil.getAuthenticationToken(jwt, userDetails);
                                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource());
                                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                                accessor.setUser(usernamePasswordAuthenticationToken);
                            }
                        } catch (UsernameNotFoundException ignored) {
                        }

                    }


                }
                return message;
            }
        });
    }
}
