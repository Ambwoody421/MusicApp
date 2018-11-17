package app.config;

import app.controller.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){}{
        register(UserController.class);
        register(GroupController.class);
        register(SessionController.class);
        register(SongController.class);
        register(PlaylistController.class);
        register(QueueController.class);
        register(AdminController.class);
    }
}
