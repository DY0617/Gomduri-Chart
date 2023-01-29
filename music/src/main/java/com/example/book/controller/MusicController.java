package com.example.book.controller;

import com.example.book.domain.Music;
import com.example.book.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;


@Controller
public class MusicController {
    private final MusicService musicService;


    @Autowired
    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }


    @GetMapping("/")
    public String home() {
        return "home";
    }


    @GetMapping("/song")
    public String list(Model model) {
        List<Music> song = musicService.findSong();
        model.addAttribute("song", song);
        return "song/musicList";
    }


    @GetMapping("/song/new")
    public String createForm() {
        return "song/inputMusicForm";
    }


    @PostMapping("/song/new")
    public String create(MusicForm form) {
        Music music = new Music();
        music.setName(form.getName());
        music.setSinger(form.getSinger());
        music.setGenre(form.getGenre());
        musicService.addMusic(music);
        return "redirect:/";
    }


    @GetMapping("/song/search")
    public String searchForm() {
        return "song/searchMusicForm";
    }


    @PostMapping("/song/search")
    public String search(MusicForm form, Model model) {
        List<Music> song = musicService.findCondSong(form);
        model.addAttribute("song", song);
        return "song/musicList";
    }
}
