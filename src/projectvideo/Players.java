
package projectvideo;

import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;

/*
 * Final Year Project 
 * @author Conor Donnelly
 * ID: 14145855
 */
public class Players {

    private SimpleStringProperty name, event;
    private Double time;

    public Players(String name, String event, Double time) {
        this.name = new SimpleStringProperty(name);
        this.event = new SimpleStringProperty(event);
        this.time = new Double(time);
    }

    public String getName() {
        return name.get();
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public String getEvent() {
        return event.get();
    }

    public void setEvent(SimpleStringProperty event) {
        this.event = event;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    void setStartTime(Duration minutes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
