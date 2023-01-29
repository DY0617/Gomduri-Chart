package com.example.book;

import com.example.book.repository.MusicRepository;
import com.example.book.repository.JdbcMusicRepository;
import com.example.book.service.MusicService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MusicService musicService() {
        return new MusicService(musicRepository());
    }

    @Bean
    public MusicRepository musicRepository() {
        return new JdbcMusicRepository(dataSource);
    }

}
