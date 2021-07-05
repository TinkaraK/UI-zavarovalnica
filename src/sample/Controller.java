package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class Controller implements Initializable {
    public GridPane mainGrid;
    public AnchorPane zavarovanec;
    public DatePicker datumRojstva;
    public TextField ime;
    public TextField priimek;
    public TextField ulica;
    public TextField hisnaStevilka;
    public TextField postnaStevilka;
    public TextField kraj;
    public RadioButton izkusenVoznik;
    public ToggleGroup izkusnje;
    public RadioButton mladiVoznik;
    public ComboBox znamka;
    public TextField oznaka;
    public ComboBox vrstaVozila;
    public ComboBox gorivo;
    public Spinner stSedezev;
    public Spinner prostornina;
    public Spinner moc;
    public ComboBox barva;
    public AnchorPane zavarovanje;
    public ToggleGroup osnovno;
    public ToggleGroup kasko;
    public AnchorPane registracija;
    public DatePicker datumRegistracije;
    public TextField registrska;
    public TextField krajRegistracije;
    public AnchorPane vozilo;
    public Button ponastavi;
    public Button shrani;
    public CheckBox zavSteklo;
    public CheckBox zavPotnik;
    public CheckBox zavTretjaOs;
    public CheckBox zavGum;
    public CheckBox zavKraja;
    public CheckBox zavToca;
    public RadioButton polno;
    public RadioButton AO;
    public RadioButton brez;
    public RadioButton AO_;
    public RadioButton odbitna;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stSedezev.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,5));
        prostornina.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 900));
        moc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(70,900));

        znamka.getItems().addAll("Audi", "Alpha Romeo", "BMW", "Citroen","Fiat", "Ford", "Honda", "Hyundai", "Kia", "Mazda", "Mercedes", "Nissan", "Opel", "Peugeot", "Porche", "Renault", "Seat", "Škoda","Toyota", "Volkswagen", "Volvo");
        vrstaVozila.getItems().addAll("osebni avto", "motor", "traktor", "tovornjak", "avtobus");
        vrstaVozila.getSelectionModel().selectFirst();
        barva.getItems().addAll("bela", "siva", "kovinska", "rjava", "rdeča", "modra", "rumena", "zlata", "vijolična", "roza");
        gorivo.getItems().addAll("bencin", "dizel", "hibrid", "električni", "plin");
        vrstaVozila.getSelectionModel().clearSelection();
        gorivo.getSelectionModel().clearSelection();
    }

    public void ponastavi() {
        ime.clear();
        ime.getStyleClass().remove("error");
        priimek.clear();
        priimek.getStyleClass().remove("error");
        datumRojstva.setValue(null);
        datumRojstva.setPromptText("dd. MM. LLLL");
        datumRojstva.getStyleClass().remove("error");
        ulica.clear();
        ulica.getStyleClass().remove("error");
        hisnaStevilka.clear();
        hisnaStevilka.getStyleClass().remove("error");
        postnaStevilka.clear();
        postnaStevilka.getStyleClass().remove("error");
        kraj.clear();
        kraj.getStyleClass().remove("error");
        znamka.getSelectionModel().clearSelection();
        znamka.getStyleClass().remove("error");
        znamka.setPromptText("Izberite/vpišite znamko");
        oznaka.clear();
        oznaka.getStyleClass().remove("error");
        vrstaVozila.getSelectionModel().clearSelection();
        vrstaVozila.setPromptText("Izberite vrsto vozila");
        vrstaVozila.getStyleClass().remove("error");
        gorivo.getSelectionModel().clearSelection();
        gorivo.getStyleClass().remove("error");
        gorivo.setPromptText("Izberite vrsto goriva");
        stSedezev.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,5));
        stSedezev.getStyleClass().remove("error");
        prostornina.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1.9, 900));
        prostornina.getStyleClass().remove("error");
        moc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(70,900));
        moc.getStyleClass().remove("error");
        stSedezev.setPromptText("Vpišite število sedežev");
        stSedezev.getStyleClass().remove("error");
        barva.getSelectionModel().clearSelection();
        barva.getStyleClass().remove("error");
        barva.setPromptText("Izberite/vpišite barvo");
        datumRegistracije.setValue(null);
        datumRegistracije.setPromptText("dd. MM. LLLL");
        datumRegistracije.getStyleClass().remove("error");
        datumRojstva.getStyleClass().remove("error");
        registrska.clear();
        registrska.getStyleClass().remove("error");
        krajRegistracije.clear();
        krajRegistracije.getStyleClass().remove("error");
        zavSteklo.setSelected(false);
        zavPotnik.setSelected(false);
        zavTretjaOs.setSelected(false);
        zavGum.setSelected(false);
        zavKraja.setSelected(false);
        zavToca.setSelected(false);
        mladiVoznik.setSelected(true);
        AO.setSelected(true);
        polno.setSelected(true);
    }
    public void ponastaviCB(ActionEvent actionEvent) {
        ponastavi();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ponastavi");
        alert.setHeaderText("Sporočilo");
        alert.setContentText("Polja so ponastavljena.");
        alert.showAndWait();
    }
    public boolean notOnlyCharacters(String str) {
        return ! Pattern.matches("[a-zA-ZčČšŠžŽćĆđĐ]+", str);
    }

    public void alertInfo(String str) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Izpolnite vsa polja");
        alert.setHeaderText("Izpolnite vsa polja.");
        alert.setContentText(str);
        alert.showAndWait();
    }


    public void shraniCB(ActionEvent actionEvent) {
        HashMap<String, Object> zavarovalnaPolica = new HashMap<>();
        boolean canSave = true;
        StringBuilder alertEmpty = new StringBuilder();
        StringBuilder alertValidation = new StringBuilder();
        //ime
        if (ime.getText().isEmpty()) {
            alertEmpty.append("Izpolnite ime.\n");
            if (! ime.getStyleClass().contains("error"))
                ime.getStyleClass().add("error");
        }
        else if (notOnlyCharacters(ime.getText())) {
            alertValidation.append("V imenu so lahko samo črke.\n");
            if (! ime.getStyleClass().contains("error"))
                ime.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("ime", ime.getText());
            ime.getStyleClass().remove("error");
        }
        //priimek

        if (priimek.getText().isEmpty()) {
            alertEmpty.append("Izpolnite priimek.\n");
            if (! priimek.getStyleClass().contains("error"))
                priimek.getStyleClass().add("error");
        }
        else if (notOnlyCharacters(priimek.getText())) {
            alertValidation.append("V priimku so lahko samo črke.\n");
            if (! priimek.getStyleClass().contains("error"))
                priimek.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("priimek", priimek.getText());
            priimek.getStyleClass().remove("error");
        }

        //datum rojstva
        if (datumRojstva.getValue() == null) {
            alertEmpty.append("Izpolnite datum rojstva.\n");
            if (! datumRojstva.getStyleClass().contains("error"))
                datumRojstva.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("datumRojstva", datumRojstva.getValue());
            datumRojstva.getStyleClass().remove("error");
        }

        // izkusnje
        if (izkusenVoznik.isSelected())
            zavarovalnaPolica.put("voznik", "izkusen");
        else
            zavarovalnaPolica.put("voznik", "mladi");

        // ulica
        if (ulica.getText().isEmpty()) {
            alertEmpty.append("Izpolnite ulico.\n");
            if (! ulica.getStyleClass().contains("error"))
                ulica.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("ulica", ulica.getText());
            ulica.getStyleClass().remove("error");
        }
        //hisna stevilka
        if (hisnaStevilka.getText().isEmpty()) {
            alertEmpty.append("Izpolnite hišno številko.\n");
            if (! hisnaStevilka.getStyleClass().contains("error"))
                hisnaStevilka.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("hisnaStevilka", hisnaStevilka.getText());
            hisnaStevilka.getStyleClass().remove("error");
        }
        // postna stevilka
        if (postnaStevilka.getText().isEmpty()) {
            alertEmpty.append("Izpolnite poštno številko.\n");
            if (! postnaStevilka.getStyleClass().contains("error"))
                postnaStevilka.getStyleClass().add("error");
        }
        else if (! Pattern.matches("[0-9]{4}", postnaStevilka.getText())) {
            alertValidation.append("Neustrezna poštna številka.\n");
            if (! postnaStevilka.getStyleClass().contains("error"))
                postnaStevilka.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("postnaStevilka", postnaStevilka.getText());
            postnaStevilka.getStyleClass().remove("error");
        }
        // kraj
        if (kraj.getText().isEmpty()) {
            alertEmpty.append("Izpolnite kraj.\n");
            if (! kraj.getStyleClass().contains("error"))
                kraj.getStyleClass().add("error");
        }
        else if (notOnlyCharacters(kraj.getText())) {
            alertValidation.append("V kraju so lahko samo črke.\n");
            if (! kraj.getStyleClass().contains("error"))
                kraj.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("kraj", kraj.getText());
            kraj.getStyleClass().remove("error");
        }


        // znamka
        if (znamka.getValue() == null) {
            alertEmpty.append("Izberite znamko.\n");
            if (! znamka.getStyleClass().contains("error"))
                znamka.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("znamka", znamka.getValue());
            znamka.getStyleClass().remove("error");
        }

        // oznaka
        if (oznaka.getText().isEmpty()) {
            alertEmpty.append("Izpolnite oznako.\n");
            if (! oznaka.getStyleClass().contains("error"))
                oznaka.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("oznaka", oznaka.getText());
            oznaka.getStyleClass().remove("error");
        }
        // vrsta vozila
        if (vrstaVozila.getValue() == null) {
            alertEmpty.append("Izberite vrsto vozila.\n");
            if (! vrstaVozila.getStyleClass().contains("error"))
                vrstaVozila.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("vrstaVozila", vrstaVozila.getValue());
            vrstaVozila.getStyleClass().remove("error");
        }

        // gorivo
        if (gorivo.getValue() == null) {
            alertEmpty.append("Izberite vrsto goriva.\n");
            if (! gorivo.getStyleClass().contains("error"))
                gorivo.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("gorivo", gorivo.getValue());
            gorivo.getStyleClass().remove("error");
        }

        // sedezi
        if (stSedezev.getValue() == null) {
            alertEmpty.append("Vpišite število sedežev.\n");
            if (! stSedezev.getStyleClass().contains("error"))
                stSedezev.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("stSedezev", stSedezev.getValue());
            stSedezev.getStyleClass().remove("error");
        }

        // prostornina
        if (prostornina.getValue() == null) {
            alertEmpty.append("Vpišite prostornino.\n");
            if (! prostornina.getStyleClass().contains("error"))
                prostornina.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("prostornina", prostornina.getValue());
            prostornina.getStyleClass().remove("error");
        }
        // moc
        if (moc.getValue() == null) {
            alertEmpty.append("Vpišite moč.\n");
            if (! moc.getStyleClass().contains("error"))
                moc.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("moc", moc.getValue());
            moc.getStyleClass().remove("error");
        }

        // barva
        if (barva.getValue() == null) {
            alertEmpty.append("Izberite barvo.\n");
            if (! barva.getStyleClass().contains("error"))
                barva.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("barva", barva.getValue());
            barva.getStyleClass().remove("error");
        }

        // datum registracije
        if (datumRegistracije.getValue() == null) {
            alertEmpty.append("Izberite datum prve registracije.\n");
            if (! datumRegistracije.getStyleClass().contains("error"))
                datumRegistracije.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("datumRegistracije", datumRegistracije.getValue());
            datumRegistracije.getStyleClass().remove("error");
        }

        // registrska
        if (registrska.getText().isEmpty()) {
            alertEmpty.append("Izpolnite registrsko označbo.\n");
            if (! registrska.getStyleClass().contains("error"))
                registrska.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("registrska", registrska.getText());
            registrska.getStyleClass().remove("error");
        }


        // kraj registracije
        if (krajRegistracije.getText().isEmpty()) {
            alertEmpty.append("Izpolnite kraj prve registracije.\n");
            if (! krajRegistracije.getStyleClass().contains("error"))
                krajRegistracije.getStyleClass().add("error");
        }
        else if (notOnlyCharacters(krajRegistracije.getText())) {
            alertValidation.append("V kraju prve registracije so lahko samo črke.\n");
            if (! krajRegistracije.getStyleClass().contains("error"))
                krajRegistracije.getStyleClass().add("error");
        }
        else {
            zavarovalnaPolica.put("krajRegistracije", krajRegistracije.getText());
            krajRegistracije.getStyleClass().remove("error");
        }

        if (! alertEmpty.isEmpty()) {
            alertInfo(String.valueOf(alertEmpty));
            canSave = false;
        }

        if (! alertValidation.isEmpty()) {
            alertInfo(String.valueOf(alertValidation));
            canSave = false;
        }
        // osnovno zavarovanje
        if (AO.isSelected())
            zavarovalnaPolica.put("AO", true);
        else
            zavarovalnaPolica.put("AO+", true);
        // kasko
        if (polno.isSelected())
            zavarovalnaPolica.put("polno", true);
        else if (brez.isSelected())
            zavarovalnaPolica.put("brez", true);
        else
            zavarovalnaPolica.put("odbitna", true);

        // dodatno
        if (zavKraja.isSelected())
            zavarovalnaPolica.put("zavKraja", zavKraja.isSelected());
        if (zavGum.isSelected())
            zavarovalnaPolica.put("zavGum", zavGum.isSelected());
        if (zavSteklo.isSelected())
            zavarovalnaPolica.put("zavSteklo", zavSteklo.isSelected());
        if (zavToca.isSelected())
            zavarovalnaPolica.put("zavToca", zavToca.isSelected());
        if (zavTretjaOs.isSelected())
            zavarovalnaPolica.put("zavTretjaOs", zavTretjaOs.isSelected());
        if (zavPotnik.isSelected())
            zavarovalnaPolica.put("zavPotnik", zavPotnik.isSelected());


        if (canSave) {
            try {
                File myObj = new File(String.format("%s%s.txt", ime.getText(), priimek.getText()));
                if (myObj.createNewFile()){
                    System.out.println(("Datoteka ustvarjena. " + myObj.getName()));
                }
                else
                    System.out.println(("Datoteka ustvarjena. " + myObj.getName()));
                FileWriter fw = new FileWriter(myObj.getName());
                for (Map.Entry atribut : zavarovalnaPolica.entrySet()) {
                    fw.write(atribut.getKey() + ": " + atribut.getValue() + "\n");
                }
                fw.close();
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);
                a1.setTitle("Podatki shranjeni");
                a1.setContentText("Podatki so shranjeni v datoteki " + myObj.getName());
                a1.setHeaderText("Sporočilo");
                a1.showAndWait();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            ponastavi();
        }

    }

    public void odpriCB(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberi 'ime'.txt datoteko ");
        File f = fc.showOpenDialog(null);
        if (f != null) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(f))){
                String line;
                String[] splitLine;
                while ((line = br.readLine()) != null) {
                    splitLine = line.split(": ");
                    zapisi(splitLine[0], splitLine[1]);

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void zapisi(String name, String value) {
        switch (name) {
            case "ime":
                ime.setText(value);
                break;
            case "priimek":
                priimek.setText(value);
                break;
            case "datumRojstva":
                datumRojstva.setValue(LocalDate.parse(value));
                break;
            case "voznik":
                if (value.equals("izkusen"))
                    izkusenVoznik.setSelected(true);
                else
                    mladiVoznik.setSelected(true);
                break;
            case "ulica":
                ulica.setText(value);
                break;
            case "hisnaStevilka":
                hisnaStevilka.setText(value);
                break;
            case "postnaStevilka":
                postnaStevilka.setText(value);
                break;
            case "kraj":
                kraj.setText(value);
                break;
            case "znamka":
                znamka.setValue(value);
                break;
            case "oznaka":
                oznaka.setText(value);
                break;
            case "vrstaVozila":
                vrstaVozila.setValue(value);
                break;
            case "gorivo":
                gorivo.setValue(value);
                break;
            case "stSedezev":
                stSedezev.increment(Integer.parseInt(value));
                break;
            case "prostornina":
                //prostornina.increment(Integer.parseInt(value));
                break;
            case "moc":
                moc.increment(Integer.parseInt(value));
                break;
            case "barva":
                barva.setValue(value);
                break;
            case "AO":
                AO.setSelected(true);
                break;
            case "AO+":
                AO_.setSelected(true);
                break;
            case "datumRegistracije":
                datumRegistracije.setValue(LocalDate.parse(value));
                break;
            case "registrska":
                registrska.setText(value);
                break;
            case "krajRegistracije":
                krajRegistracije.setText(value);
                break;
            case "zavKraja":
                zavKraja.setSelected(true);
                break;
            case "zavPotnik":
                zavPotnik.setSelected(true);
                break;
            case "zavTretjaOs":
                zavTretjaOs.setSelected(true);
                break;
            case "zavSteklo":
                zavSteklo.setSelected(true);
                break;
            case "zavToca":
                zavToca.setSelected(true);
                break;
            case "zavGum":
                zavGum.setSelected(true);
                break;
            case "polno":
                polno.setSelected(true);
                break;
            case "brez":
                brez.setSelected(true);
                break;
            case "odbitna":
                odbitna.setSelected(true);
                break;
        }

    }

    public void zapriCB(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void oAvtorju(ActionEvent actionEvent) {
        ponastavi();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacije");
        alert.setHeaderText("O avtorju");
        alert.setContentText("Avtorica programa: Tinkara Končan.");
        alert.showAndWait();
    }

    public void pomocCB(ActionEvent actionEvent) {
        ponastavi();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacije");
        alert.setHeaderText("Pomoč");
        alert.setContentText("Vpišite vse podatke. \nZa shranjevanje pritisnite gumb 'Shrani' ali pritisnite kombinacijo tipk CTRL + S.\n" +
                "Za odprtje datoteke pritisnite CTRL + O ali odprite ukaz v meniju.\n" +
                "Za ponastavitev podatkov pritisnite gumb, ali pritisnite CTRL + P.");
        alert.showAndWait();
    }
}
