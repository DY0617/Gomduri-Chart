package com.example.book.service;

import com.example.book.controller.MusicForm;
import com.example.book.domain.Music;
import com.example.book.repository.MusicRepository;

import java.util.List;

public class MusicService {
    private final MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    /* 전체 음악 목록 조회
     *
     */
    public List<Music> findSong() {
        return musicRepository.findAll();
    }
    /* 음악 추가
     *
    */
    public Long addMusic(Music music) {
        musicRepository.save(music);
        return music.getId();
    }
    public List<Music> findCondSong(MusicForm form) {
        return musicRepository.findCond(form);
    }
}