package hi.verkefni.vinnsla.data;

import hi.verkefni.vinnsla.Flokkur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventData {
    public String heiti;
    public Flokkur flokkur;
    public LocalDate dagsetning;
    public String kynningarmyndband; // File path to media

    // 🔐 Access control
    public boolean grantToAll = false;
    public List<String> accessList = new ArrayList<>();
}
