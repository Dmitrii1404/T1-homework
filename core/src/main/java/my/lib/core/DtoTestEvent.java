package my.lib.core;

public class DtoTestEvent {
    private String title;

    private String event;

    public DtoTestEvent() {}

    public DtoTestEvent(String title, String event) {
        this.title = title;
        this.event = event;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
}
