package hi.verkefni.vinnsla;

import hi.verkefni.vinnsla.data.EventData;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.media.Media;

import java.time.LocalDate;

public class EventModel {
    // JavaFX-eiginleikar fyrir heiti, flokk, dagsetningu og kynningarmyndband
    private final SimpleStringProperty heiti = new SimpleStringProperty();
    private final SimpleObjectProperty<Flokkur> flokkur = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> dagsetning = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Media> kynningarmyndband = new SimpleObjectProperty<>();

    /**
     * Getter fyrir heiti property
     *
     * @return heiti
     */
    public SimpleStringProperty heitiProperty() {
        return heiti;
    }

    /**
     * Getter fyrir flokkur property
     *
     * @return flokkur
     */
    public SimpleObjectProperty<Flokkur> flokkurProperty() {
        return flokkur;
    }

    /**
     * Getter fyrir dagsetning property
     *
     * @return dagsetning
     */
    public SimpleObjectProperty<LocalDate> dagsetningProperty() {
        return dagsetning;
    }

    /**
     * Getter fyrir kynningarmyndband property
     *
     * @return kynningarmyndband
     */
    public SimpleObjectProperty<Media> kynningarmyndbandProperty() {
        return kynningarmyndband;
    }

    /**
     * Athugar hvort nafnið sé ekki tómt eða null.
     *
     * @return true ef viðburðurinn hefur heiti
     */
    public boolean hasName() {
        String value = heiti.get();
        return value != null && !value.isBlank();
    }

    /**
     * Athugar hvort viðburðurinn sé tómur (engin gögn slegin inn).
     *
     * @return true ef ekkert heiti, flokkur eða dagsetning hefur verið valið
     */
    public boolean isEmpty() {
        return (heiti.get() == null || heiti.get().isBlank()) &&
                flokkur.get() == null &&
                dagsetning.get() == null;
    }

    /**
     * Umbreytir EventModel í EventData til að hægt sé að vista sem JSON.
     *
     * @return nýr EventData hlutur með öllum gildum
     */
    public EventData toData() {
        EventData data = new EventData();
        data.heiti = heiti.get();
        data.flokkur = flokkur.get();
        data.dagsetning = dagsetning.get();
        data.kynningarmyndband = kynningarmyndband.get() != null
                ? kynningarmyndband.get().getSource()
                : null;
        return data;
    }

    /**
     * Býr til nýjan EventModel út frá EventData (t.d. eftir að gögn eru lesin úr skrá).
     *
     * @param data EventData hlutur sem inniheldur vistuð gögn
     * @return nýr EventModel tilbúinn til að birta í viðmóti
     */
    public static EventModel fromData(EventData data) {
        EventModel model = new EventModel();
        model.heiti.set(data.heiti);
        model.flokkur.set(data.flokkur);
        model.dagsetning.set(data.dagsetning);
        if (data.kynningarmyndband != null) {
            model.kynningarmyndband.set(new Media(data.kynningarmyndband));
        }
        return model;
    }
}
