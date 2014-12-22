package cz.encircled.ioc.model;

import cz.encircled.ioc.annotation.Component;
import cz.encircled.ioc.annotation.Wired;

import java.util.List;
import java.util.Map;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Component
public class House extends AbstractHouse implements Building {

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
}
