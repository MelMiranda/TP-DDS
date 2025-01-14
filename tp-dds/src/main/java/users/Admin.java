package users;

import java.util.List;
import java.util.concurrent.Callable;

import dao.model.Action;
import domain.EmailErrorProcessResolution;
import domain.ErrorProcessResolution;
import domain.RepeatErrorProcessResolution;
import domain.Schedule;
import internalService.PoiService;
import poi.CGP;
import poi.CGPService;
import poi.Poi;

import javax.persistence.*;


public class Admin {

    private String nombre;
    private String contrasenia;
    private List<Action> actions;
    private PoiService poiService;


    private String mail;


    private ErrorProcessResolution errorResolution;


    public Admin(List<Action> actions, String nombre, String contrasenia, String mail, String resolutionType) {
        this.nombre=nombre;
        this.contrasenia=contrasenia;
        actions=actions;
        this.mail = mail;
        this.poiService = PoiService.getInstance();

        if (resolutionType.equalsIgnoreCase( "Email")) {
            errorResolution = new EmailErrorProcessResolution();
        } else if (resolutionType.equalsIgnoreCase( "Repeat")) {
            errorResolution = new RepeatErrorProcessResolution();
        }
    }


    public void turnOffAPoi() {
        Admin admin = this;
        errorResolution.errorResolution(poiService.getProcessService().turnOffAPoi(this), this, new Callable<String>() {

            @Override
            public String call() {
                return poiService.getProcessService().turnOffAPoi(admin);
            }
        });
    }

    public void updateComercialShops(String path) {
        Admin admin = this;
        new Thread(() ->
                errorResolution.errorResolution(poiService.getProcessService().updateComercialShops(path, this), this, new Callable<String>() {

                    @Override
                    public String call() {
                        return poiService.getProcessService().updateComercialShops(path, admin);
                    }
                })).start();

    }

    public void addActionsToUser(String nombre, List actions) {
        Admin admin = this;
        new Thread(() ->
                errorResolution.errorResolution(poiService.getProcessService().addActionsToUser(nombre, actions, this), this, new Callable<String>() {
                    @Override
                    public String call() {
                        return poiService.getProcessService().addActionsToUser(nombre, actions, admin);
                    }
                })).start();

    }

    public void multiplyProcess(List<Runnable> process) {
        process.forEach(methods -> methods.run());
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public ErrorProcessResolution getErrorResolution() {
        return errorResolution;
    }

    public void setErrorResolution(ErrorProcessResolution errorResolution) {
        this.errorResolution = errorResolution;
    }




    public Admin(String name){
        nombre=name;
        poiService = PoiService.getInstance();
    }

    public Admin() {
        poiService = PoiService.getInstance();

    }
    public boolean addTerminal(Terminal terminal) {
        poiService.getTerminales().add(terminal);
        return true;
    }

    @Transient
    public boolean removeTerminal(String name) {
        int index = 0;
        boolean status = false;
        for (Terminal currenTerminal : poiService.getTerminales()) {
            if (currenTerminal.getNombre().equalsIgnoreCase(name)) {
                poiService.getTerminales().remove(index);
                status = true;
            }
            index++;
        }
        return status;
    }


    public boolean removePoi(String poiName) {
        int index = 0;

        for (Poi currentPoi : poiService.getAllPois()) {

            if (currentPoi.getName().equalsIgnoreCase(poiName)) {
                poiService.getAllPois().remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    public void addPoi(Poi poi) {
        poiService.addPoi(poi);
    }

    // Consultar o interpretar otras formas.
    //NO actualiza la lista cuando actualizo el elemento.
    //EN cualquier momento borro todo el elemento a la meirda y pongo el nuevo pasado por parametro.
    public boolean modifyPoi(Poi poi, String poiName) {

        for (Poi currentPoi : poiService.getAllPois()) {

            if (currentPoi.getName().equalsIgnoreCase(poiName)) {
                currentPoi = poi;
                return true;

            }

        }
        return false;
    }

    public boolean addCGPServiceToCGP(String cgpName, CGPService cgpService) {

        for (Poi currentPoi : poiService.getAllPois()) {
            if (currentPoi.getName().equalsIgnoreCase(cgpName)
                    && currentPoi.getType().equalsIgnoreCase("CGP")) {

                CGP cgp = (CGP) currentPoi;
                cgp.addService(cgpService);
                return true;
            }
        }
        return false;

    }

    public PoiService getPoiService() {
        return poiService;
    }

    public void setPoiService(PoiService poiService) {
        this.poiService = poiService;
    }

    public boolean addScheduleToCGPService(String nameOfCGP,
                                           String serviceName, String hourMax, String hourMin) {

        for (Poi currentPoi : poiService.getAllPois()) {
            if (currentPoi.getType() == "CGP"
                    && currentPoi.getName().equalsIgnoreCase(nameOfCGP)) {
                CGP cgp = (CGP) currentPoi;
                for (CGPService currentService : cgp.getServices()) {
                    if (currentService.getServiceName().equalsIgnoreCase(
                            serviceName)) {
                        Schedule schedule = new Schedule(hourMin, hourMax);
                        currentService.getRangeOfAtention().addSchedule(
                                schedule);
                        return true;
                    }
                }
            }
        }
        return false;

    }


    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
