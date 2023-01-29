package com.example.book.repository;

import com.example.book.controller.MusicForm;
import com.example.book.domain.Music;

import java.util.List;

public interface MusicRepository {
    List<Music> findAll();
    Music save(Music music);
    List<Music> findCond(MusicForm form);
}
