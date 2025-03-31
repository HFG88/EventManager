package hi.verkefni.vidmot;

import javafx.event.ActionEvent;

public class MenuController {
    /**
     * Action handlerinn fyrir "Nýr" valkostinn
     *
     * @param actionEvent
     */
    public void onNyr(ActionEvent actionEvent) {
        System.out.println("Clicked Nýr");
        EventManagerController controller = EventManagerApplication.getController();
        controller.nyr();
    }

    /**
     * Action handlerinn fyrir "Opna" valkostinn
     *
     * @param actionEvent
     */
    public void onOpna(ActionEvent actionEvent) {
        System.out.println("Clicked Opna");
        EventManagerController controller = EventManagerApplication.getController();
        controller.opna();

    }

    /**
     * Action handlerinn fyrir "vista" valkostinn
     *
     * @param actionEvent
     */
    public void onVista(ActionEvent actionEvent) {
        System.out.println("Clicked Vista");
        EventManagerController controller = EventManagerApplication.getController();
        controller.vista();
    }

    /**
     * Action handlerinn fyrir "Hætta" valkostinn
     *
     * @param actionEvent
     */
    public void onHaetta(ActionEvent actionEvent) {
        System.out.println("Clicked Hætta");
        EventManagerController controller = EventManagerApplication.getController();
        controller.haetta();
    }

    /**
     * Action handlerinn fyrir "Breyta" valkostinn
     *
     * @param actionEvent
     */
    public void onBreyta(ActionEvent actionEvent) {
        System.out.println("Clicked Breyta");
        EventManagerController controller = EventManagerApplication.getController();
        controller.breyta();
    }

    /**
     * Action handlerinn fyrir "Um" valkostinn
     *
     * @param actionEvent
     */
    public void onUm(ActionEvent actionEvent) {
        System.out.println("Clicked Um");
        EventManagerController controller = EventManagerApplication.getController();
        controller.um();
    }
}
