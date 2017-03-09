package com.ComeOnBaby.controller;


import com.ComeOnBaby.model.Notice;
import com.ComeOnBaby.service.NoticeService;
import com.ComeOnBaby.util.SaveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/notice")
@SessionAttributes("roles")
public class NoticeEventController {


    @Autowired
    NoticeService noticeService;


    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ModelAndView noticeEvents() {
        ModelAndView noticeEvents = new ModelAndView("noticeEvent");

        ArrayList<Notice> notices = (ArrayList<Notice>) noticeService.getAllNotice();

        noticeEvents.addObject("notices", notices);

        return noticeEvents;
    }

    @RequestMapping(value = "/add-notice", method = RequestMethod.GET)
    public ModelAndView addNotice() {
        ModelAndView addNotice = new ModelAndView("noticeEventEdit");
        addNotice.addObject("isNew", true);
        return addNotice;
    }


    @RequestMapping(value = "/edit-notice/{noticeId}", method = RequestMethod.GET)
    public ModelAndView editNotice(@PathVariable Long noticeId) {
        ModelAndView editNotice = new ModelAndView("noticeEventEdit");

        Notice notice = noticeService.get(noticeId);

        editNotice.addObject("isNew", false);
        editNotice.addObject("notice", notice);

        return editNotice;

    }

    @RequestMapping(value = "/delete-notice/{id}", method = RequestMethod.GET)
    public ModelAndView deleteNotice(@PathVariable Long id) {

        Notice notice = noticeService.get(id);

        noticeService.deleteNotice(notice);

        return new ModelAndView("redirect:/notice/events");
    }



    @RequestMapping(value = "/save-new-notice", method = RequestMethod.POST)
    public ModelAndView saveNewRecipe(@RequestParam(value = "id") String id, @RequestParam("title") String title, @RequestParam("text") String noticeText, @RequestParam("filePicture[]") MultipartFile[] files) {

        if (id.equals("")) {

            Notice notice = new Notice();

            notice.setDate(new Date());
            notice.setTitle(title);
            notice.setText(noticeText);

            //Save to file

            StringBuilder noticeFileNames = new StringBuilder();

            if (!files[0].isEmpty()) {
                String pathToSaveFile = "pictures/";
                SaveFile saveFile = new SaveFile(pathToSaveFile, files);
                saveFile.saveFileAndGetName();

                ArrayList<String> fileNames = saveFile.saveFileAndGetName();
                for (String name : fileNames){
                    noticeFileNames.append(name+"|");
                }
                notice.setImages(noticeFileNames.toString());

                noticeService.addNewNotice(notice);
            }

        } else {

            Notice notice = noticeService.get(Long.valueOf(id));

            notice.setDate(new Date());
            notice.setTitle(title);
            notice.setText(noticeText);

            StringBuilder noticeFileNames = new StringBuilder();

            //Save to file
            if (files.length != 0) {
                if (!files[0].isEmpty()) {
                    String pathToSaveFile = "pictures/";
                    SaveFile saveFile = new SaveFile(pathToSaveFile, files);
                    ArrayList<String> fileNames = saveFile.saveFileAndGetName();
                    for (String name : fileNames){
                        noticeFileNames.append(name+"|");
                    }
                    notice.setImages(noticeFileNames.toString());
                }
            }
            noticeService.updateNotice(notice);
        }

        return new ModelAndView("redirect:/notice/events");
    }
}
