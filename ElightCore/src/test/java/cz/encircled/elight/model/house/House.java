package cz.encircled.elight.model.house;


import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Component
public class House extends AbstractHouse implements Building {

    @Wired
    public House self;

    @Wired
    private List<Window> windows;

    @Wired
    private Window[] windowsArray;

    @Wired
    private Map<Window, String> windowAsKeyMap;

    @Wired
    private Map<String, Window> windowAsValueMap;

    @Wired
    private Door door;

    private Door doorViaSetter;

    private Door doorVia330Setter;

    private List<Window> windowsViaSetter;

    private Window[] windowsArrayViaSetter;

    private Map<Window, String> windowAsKeyMapViaSetter;

    private Map<String, Window> windowAsValueMapViaSetter;

    private Map<String, Window> windowAsValueMapViaGetter;

    private List<Window> windowsVia330Setter;

    private Window[] windowsArrayVia330Setter;

    private Map<Window, String> windowAsKeyMapVia330Setter;

    private Map<String, Window> windowAsValueMapVia330Setter;

    public List<Window> getWindows() {
        return windows;
    }

    public Map<String, Window> getWindowAsValueMap() {
        return windowAsValueMap;
    }

    public Map<Window, String> getWindowAsKeyMap() {
        return windowAsKeyMap;
    }

    public Window[] getWindowsArray() {
        return windowsArray;
    }

    @Override
    public Door getDoor() {
        return door;
    }

    public boolean isInitCalled() {
        return initCalled;
    }

    public boolean isDestroyCalled() {
        return destroyCalled;
    }

    public Door getDoorVia330Setter() {
        return doorVia330Setter;
    }

    public Door getDoorViaSetter() {
        return doorViaSetter;
    }

    @Wired
    public void setDoorViaSetter(Door doorViaSetter) {
        this.doorViaSetter = doorViaSetter;
    }

    @Inject
    public void setDoorVia330Setter(Door doorVia330Setter) {
        this.doorVia330Setter = doorVia330Setter;
    }

    public List<Window> getWindowsViaSetter() {
        return windowsViaSetter;
    }

    @Wired
    public void setWindowsViaSetter(List<Window> windowsViaSetter) {
        this.windowsViaSetter = windowsViaSetter;
    }

    public Window[] getWindowsArrayViaSetter() {
        return windowsArrayViaSetter;
    }

    @Wired
    public void setWindowsArrayViaSetter(Window[] windowsArrayViaSetter) {
        this.windowsArrayViaSetter = windowsArrayViaSetter;
    }

    public Map<Window, String> getWindowAsKeyMapViaSetter() {
        return windowAsKeyMapViaSetter;
    }

    @Wired
    public void setWindowAsKeyMapViaSetter(Map<Window, String> windowAsKeyMapViaSetter) {
        this.windowAsKeyMapViaSetter = windowAsKeyMapViaSetter;
    }

    public Map<String, Window> getWindowAsValueMapViaSetter() {
        return windowAsValueMapViaSetter;
    }

    @Wired
    public void setWindowAsValueMapViaSetter(Map<String, Window> windowAsValueMapViaSetter) {
        this.windowAsValueMapViaSetter = windowAsValueMapViaSetter;
    }

    public List<Window> getWindowsVia330Setter() {
        return windowsVia330Setter;
    }

    @Inject
    public void setWindowsVia330Setter(List<Window> windowsVia330Setter) {
        this.windowsVia330Setter = windowsVia330Setter;
    }

    public Window[] getWindowsArrayVia330Setter() {
        return windowsArrayVia330Setter;
    }

    @Inject
    public void setWindowsArrayVia330Setter(Window[] windowsArrayVia330Setter) {
        this.windowsArrayVia330Setter = windowsArrayVia330Setter;
    }

    public Map<Window, String> getWindowAsKeyMapVia330Setter() {
        return windowAsKeyMapVia330Setter;
    }

    @Inject
    public void setWindowAsKeyMapVia330Setter(Map<Window, String> windowAsKeyMapVia330Setter) {
        this.windowAsKeyMapVia330Setter = windowAsKeyMapVia330Setter;
    }

    public Map<String, Window> getWindowAsValueMapVia330Setter() {
        return windowAsValueMapVia330Setter;
    }

    @Inject
    public void setWindowAsValueMapVia330Setter(Map<String, Window> windowAsValueMapVia330Setter) {
        this.windowAsValueMapVia330Setter = windowAsValueMapVia330Setter;
    }

    @Wired
    public Map<String, Window> getWindowAsValueMapViaGetter() {
        return windowAsValueMapViaGetter;
    }

}
