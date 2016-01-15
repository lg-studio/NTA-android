package com.zl.android.repository;

import com.zl.android.domain.features.catalog.model.Course;
import com.zl.android.domain.features.catalog.model.Episode;
import com.zl.android.domain.features.catalog.model.Scene;
import com.zl.android.domain.features.catalog.model.TaskStep;
import com.zl.android.repository.features.catalog.model.WebCourse;
import com.zl.android.repository.features.catalog.model.WebEpisode;
import com.zl.android.repository.features.catalog.model.WebScene;
import com.zl.android.repository.features.profile.model.WebTaskStepAnswer;

import java.util.ArrayList;
import java.util.List;

public final class ModelMapping {

    private ModelMapping() {}

    public static Course toCourse(WebCourse webCourse) {
        Course semester = new Course();
        semester.id = webCourse.id;
        semester.name = webCourse.name;
        semester.desc = webCourse.desc;
        return semester;
    }

    public static Episode toEpisode(WebEpisode webEpisode) {
        Episode episode = new Episode();
        episode.episodeId = webEpisode.episodeId;
        episode.name = webEpisode.name;
        episode.desc = webEpisode.desc;
        episode.scenes = toScenes(webEpisode.scenes);
        return episode;
    }

    public static Scene[] toScenes(WebScene[] webScenes) {
        if (webScenes != null) {
            Scene[] scenes = new Scene[webScenes.length];
            for (int i = 0, size = webScenes.length; i < size; i++) {
                scenes[i] = toScene(webScenes[i]);
            }
            return scenes;
        }
        return null;
    }

    public static Scene toScene(WebScene webScene) {
        Scene scene = new Scene();
        scene.sceneId = webScene.sceneId;
        scene.name = webScene.name;
        scene.desc = webScene.desc;
        scene.taskIds = webScene.taskIds;
        return scene;
    }

    public static List<Episode> toEpisodes(List<WebEpisode> webEpisodes) {
        if (webEpisodes != null) {
            List<Episode> episodes = new ArrayList<>();
            for(int i = 0, size = webEpisodes.size(); i < size; i++) {
                episodes.add(toEpisode(webEpisodes.get(i)));
            }
            return episodes;
        }
        return null;
    }

    public static List<Course> toCourse(List<WebCourse> webCourse) {
        if (webCourse != null) {
            List<Course> semesters = new ArrayList<>();
            for(int i = 0, size = webCourse.size(); i < size; i++) {
                semesters.add(toCourse(webCourse.get(i)));
            }
            return semesters;
        }
        return null;
    }

    public static WebTaskStepAnswer toWebTaskStepAnswer(TaskStep taskStep) {
        WebTaskStepAnswer answer = new WebTaskStepAnswer();
        answer.taskId = taskStep.taskId;
        answer.taskStepIndex = taskStep.index;
        answer.answerText = taskStep.progress.answerText;
        return answer;
    }
}
