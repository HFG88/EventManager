package vinnsla;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.media.Media;

import java.time.LocalDate;

public class EventModel {
    private final SimpleStringProperty heiti = new SimpleStringProperty();
    private final SimpleObjectProperty<Flokkur> flokkur = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> dagsetning = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Media> kynningarmyndband = new SimpleObjectProperty<>();

    /**
     * Getter fyrir heiti property-ið
     *
     * @return SimpleStringProperty heiti
     */
    public SimpleStringProperty heitiProperty() {
        return heiti;
    }

    /**
     * getter fyrir heiti property value
     *
     * @return heiti value
     */
    public String getHeiti() {
        return heiti.get();
    }

    /**
     * Getter fyrir kynningarmyndband property-ið
     *
     * @return SimpleObjectProperty  kynningarmyndband (Media)
     */
    public SimpleObjectProperty<Media> kynningarmyndbandProperty() {
        return kynningarmyndband;
    }

    /**
     * Getter fyrir dagsetning property-ið
     *
     * @return SimpleObjectProperty  dagsetning (LocalDate)
     */
    public SimpleObjectProperty<LocalDate> dagsetningProperty() {
        return dagsetning;
    }

    /**
     * Getter fyrir flokkur property-ið
     *
     * @return SimpleObjectProperty  flokkur (Flokkur)
     */
    public SimpleObjectProperty<Flokkur> flokkurProperty() {
        return flokkur;
    }

    /**
     * Kannar hvort nafnið sé tómt eða ekki
     *
     * @return true ef það er nafn, false ef ekki
     */
    public boolean hasName() {
        return (getHeiti() != null && !getHeiti().isBlank());
    }

    /**
     * Athugar hvort viðburðurinn sé tómur (engin gögn slegin inn).
     * Notað t.d. þegar ákveða á hvort hægt sé að endurnýta EventView.
     *
     * @return true ef ekkert heiti, flokkur eða dagsetning hefur verið valið, annars false
     */
    public boolean isEmpty() {
        return (getHeiti() == null || getHeiti().isBlank()) &&
                (flokkur.get() == null) &&
                (dagsetning.get() == null);
    }


    @Override
    public String toString() {
        return "EventModel{" +
                "heiti=" + heiti.get() +
                ", flokkur=" + flokkur.get() +
                ", dagsetning=" + dagsetning.get() +
                ", kynningarmyndband=" + kynningarmyndband.get() +
                '}';
    }

    /**
     * Setter fyrir kynningarmyndband value
     * @param media media
     */
    public void setKynningarmyndband(Media media) {
        kynningarmyndband.set(media);
    }

}
