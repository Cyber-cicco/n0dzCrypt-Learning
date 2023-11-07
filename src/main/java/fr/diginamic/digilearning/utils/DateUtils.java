package fr.diginamic.digilearning.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.diginamic.digilearning.entities.CoursPlanifie;
import fr.diginamic.digilearning.entities.Evenement;
import fr.diginamic.digilearning.entities.PlageDate;
import fr.diginamic.digilearning.entities.TjmSociete;
import fr.diginamic.digilearning.entities.enums.TypeEvenement;
import fr.diginamic.digilearning.entities.occupation.OccupationSalle;
import fr.diginamic.digilearning.exception.DateException;
import fr.diginamic.digilearning.utils.calendrier.Calendrier;
import fr.diginamic.digilearning.utils.calendrier.Jour;
import fr.diginamic.digilearning.utils.calendrier.Semaine;

/**
 * Classe utilitaire qui fournit des méthodes de calcule de dates et de
 * transformation.
 *
 * @author DIGINAMIC
 *
 */
public final class DateUtils {

    /** UTC : Nom du fuseau horaire UTC */
    private static final String FUSEAU_UTC = "UTC";

    /** FUSEAU_EUROPE_PARIS : Nom du fuseau horaire de Paris */
    private static final String FUSEAU_EUROPE_PARIS = "Europe/Paris";

    /** EMPTY_STRING : chaine vide */
    private static final String EMPTY_STRING = "";

    /** DEFAULT_PATTERN : Pattern par défaut de formatage des dates */
    public static final String DEFAULT_PATTERN = "dd/MM/yyyy";

    /** MOMENT_PICKER_PATTERN : Pattern utilisé pour les dates pickers */
    public static final String MOMENT_PICKER_PATTERN = "dd-MM-yyyy";

    /** DEFAULT_PATTERN_TIME */
    public static final String DEFAULT_PATTERN_TIME = "dd/MM/yyyy HH:mm:ss";

    /** ISO_8601_PATTERN_TIME */
    public static final String ISO_8601_PATTERN_TIME = "yyyy-MM-dd'T'HH:mm:ss";

    /** MOMENT_PATTERN_TIME */
    public static final String MOMENT_PATTERN_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /** ISO_8601_PATTERN */
    public static final String ISO_8601_PATTERN = "yyyy-MM-dd";

    /** Retourne le décalage horaire en nombre d'heures entre l'heure française et l'heure UTC.
     * @return long
     */
    public static long getDecalageHoraireHeuresAvecUTC() {
        ZoneId utc = ZoneId.of(FUSEAU_UTC);
        LocalDateTime dateTimeUTC = ZonedDateTime.now(utc).toLocalDateTime();

        ZoneId paris = ZoneId.of(FUSEAU_EUROPE_PARIS);
        LocalDateTime dateTimeParis = ZonedDateTime.now(paris).toLocalDateTime();

        return ChronoUnit.HOURS.between(dateTimeUTC, dateTimeParis);
    }

    /**
     * Calcule une date de fin en fonction d'une date de début, d'une liste de jours
     * fermés et d'une durée en jours
     *
     * @param dateDebut date de début (référence)
     * @param fermes    liste des jours fermés (hors week-ends)
     * @param duree     durée en jours
     * @return {@link LocalDate} date de fin
     */
    public static LocalDate calculeDateFin(LocalDate dateDebut, List<? extends Evenement> fermes, int duree) {

        return calculeDateFin(dateDebut, fermes, duree, false);
    }

    /**
     * Calcule une date de fin en fonction d'une date de début, d'une liste de jours
     * fermés et d'une durée en jours
     *
     * @param dateDebut                date de début (référence)
     * @param fermes                   liste des jours fermés (hors week-ends)
     * @param duree                    durée en jours
     * @param congesOfficielUniquement indique s'il ne faut tenir compte que des
     *                                 congés officiels ou de toutes les fermetures
     *                                 spécifiques (sessions, centres d'examens)
     * @return {@link LocalDate} date de fin
     */
    public static LocalDate calculeDateFin(LocalDate dateDebut, List<? extends Evenement> fermes, int duree,
                                           boolean congesOfficielUniquement) {

        int compteur = 1;
        if (duree < 0) {
            compteur = 0;
        }
        LocalDate dateFin = dateDebut;
        while (compteur < Math.abs(duree)) {
            if (duree > 0) {
                dateFin = dateFin.plus(1, ChronoUnit.DAYS);
            } else {
                dateFin = dateFin.minus(1, ChronoUnit.DAYS);
            }
            if (isOuvert(dateFin, fermes, congesOfficielUniquement)) {
                compteur++;
            }
        }
        return dateFin;
    }

    /**
     * Retourne true si la date passé en paramètre est un jour ouvert (jour de
     * semaine) et n'est pas un jour fermé (congé ou RTT employeur)
     *
     * @param fermes liste des jours fermés (hors week-ends)
     * @return boolean
     */
    public static boolean isOuvert(Jour jour, List<? extends Evenement> fermes) {
        return isOuvert(jour.getDate(), fermes, false);
    }

    /**
     * Retourne true si la date passé en paramètre est un jour ouvert (jour de
     * semaine) et n'est pas un jour fermé (congé ou RTT employeur)
     *
     * @param date   date à contrôler
     * @param fermes liste des jours fermés (hors week-ends)
     * @return boolean
     */
    public static boolean isOuvert(LocalDate date, List<? extends Evenement> fermes) {
        return isOuvert(date, fermes, false);
    }

    /**
     * Retourne true si la date passé en paramètre est un jour ouvert (jour de
     * semaine) et n'est pas un jour fermé (congé ou RTT employeur)
     *
     * @param date                     date à contrôler
     * @param fermes                   liste des jours fermés (hors week-ends)
     * @param congesOfficielUniquement indique s'il ne faut tenir compte que des
     *                                 congés officiels ou de toutes les fermetures
     *                                 spécifiques (sessions, centres d'examens)
     * @return boolean
     */
    public static boolean isOuvert(LocalDate date, List<? extends Evenement> fermes, boolean congesOfficielUniquement) {
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return false;
        }
        for (Evenement ferme : fermes) {
            if (!congesOfficielUniquement || (congesOfficielUniquement && ferme.getType() != null
                    && ferme.getType().equals(TypeEvenement.CONGE_OFFICIEL))) {
                if ((date.isAfter(ferme.getDateDebut()) || date.equals(ferme.getDateDebut()))
                        && (date.equals(ferme.getDateFin()) || date.isBefore(ferme.getDateFin()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retourne la date du prochain jour ouvré à partir de la date passée en
     * paramètre. Le calcul se fait sur la base d'une liste de jours dits fermés.
     * <br>
     * Au plus près, la date retournée est celle du lendemain.
     *
     * @param date   date qui sert de base au calcul
     * @param fermes liste des jours fermés.
     * @return {@link LocalDate}
     */
    public static LocalDate getNextJourOuvre(LocalDate date, List<? extends Evenement> fermes) {

        return getNextJourOuvre(date, fermes, false);
    }

    /**
     * Retourne la date du prochain jour ouvré à partir de la date passée en
     * paramètre. Le calcul se fait sur la base d'une liste de jours dits fermés.
     * <br>
     * Au plus près, la date retournée est celle du lendemain.
     *
     * @param date                     date qui sert de base au calcul
     * @param fermes                   liste des jours fermés.
     * @param congesOfficielUniquement indique s'il ne faut tenir compte que des
     *                                 congés officiels ou de toutes les fermetures
     *                                 spécifiques (sessions, centres d'examens)
     * @return {@link LocalDate}
     */
    public static LocalDate getNextJourOuvre(LocalDate date, List<? extends Evenement> fermes,
                                             boolean congesOfficielUniquement) {

        LocalDate nextDateOuvree = date;
        do {
            nextDateOuvree = nextDateOuvree.plus(1, ChronoUnit.DAYS);
        } while (!isOuvert(nextDateOuvree, fermes, congesOfficielUniquement));

        return nextDateOuvree;
    }

    /**
     * Retourne la date heure courante pour le fuseau Europe/Paris
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(ZoneId.of(FUSEAU_EUROPE_PARIS));
    }

    /**
     * Convertit une date en String avec le pattern passé en paramètre (ex:
     * dd/MM/yyyy).<br>
     * Si la date est null ou le pattern est null, la méthode retourne une chaine de
     * caractère vide.
     *
     * @param date    date à convertir
     * @param pattern pattern de conversion
     * @return String
     */
    public static String toString(LocalDate date, String pattern) {
        if (date == null || pattern.isEmpty()) {
            return EMPTY_STRING;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /**
     * Convertit une date en String avec le pattern passé en paramètre (ex:
     * dd/MM/yyyy).<br>
     * Si la date est null ou le pattern est null, la méthode retourne une chaine de
     * caractère vide.
     * Le 3ème paramètre correspond aux paramètres culturels. Ex: Locale.FRANCE
     *
     * @param date    date à convertir
     * @param pattern pattern de conversion
     * @param locale  paramètres culturels
     * @return String
     */
    public static String toString(LocalDate date, String pattern, Locale locale) {
        if (date == null || pattern.isEmpty()) {
            return EMPTY_STRING;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        return date.format(formatter);
    }

    /**
     * Convertit une date en String avec le pattern passé en paramètre (ex:
     * dd/MM/yyyy).<br>
     * Si la date est null ou le pattern est null, la méthode retourne une chaine de
     * caractère vide.
     *
     * @param date    date à convertir
     * @param pattern pattern de conversion
     * @return String
     */
    public static String toString(Date date, String pattern) {
        if (date == null || pattern.isEmpty()) {
            return EMPTY_STRING;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * Convertit une date en String avec le pattern passé en paramètre (ex:
     * dd/MM/yyyy).<br>
     * Si la date est null ou le pattern est null, la méthode retourne une chaine de
     * caractère vide.
     *
     * @param date    date à convertir
     * @param pattern pattern de conversion
     * @return String
     */
    public static String toString(Timestamp date, String pattern) {
        if (date == null || pattern.isEmpty()) {
            return EMPTY_STRING;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * Convertit une date en String avec le pattern par défaut (dd/MM/yyyy)
     *
     * @param date date à convertir
     * @return String
     */
    public static String toString(LocalDate date) {
        return toString(date, DEFAULT_PATTERN);
    }

    /**
     * Convertit une date en String avec le pattern par défaut (dd/MM/yyyy)
     *
     * @param date date à convertir
     * @return String
     */
    public static String toString(Timestamp date) {
        return toString(date, DEFAULT_PATTERN_TIME);
    }

    /**
     * Convertit une date en String avec le pattern par défaut (dd/MM/yyyy)
     *
     * @param date date à convertir
     * @return String
     */
    public static String toString(Date date) {
        return toString(date, DEFAULT_PATTERN);
    }

    /**
     * Convertit une date/heure en String avec le pattern passé en paramètre (ex:
     * dd/MM/yyyy HH:mm:ss).<br>
     * Si la date est null ou le pattern est null, la méthode retourne une chaine de
     * caractère vide.
     *
     * @param date    date à convertir
     * @param pattern pattern de conversion
     * @return String
     */
    public static String toString(LocalDateTime date, String pattern) {
        if (date == null || pattern.isEmpty()) {
            return EMPTY_STRING;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /**
     * Convertit une date/heure en String avec le pattern par défaut (dd/MM/yyyy
     * HH:mm:ss)
     *
     * @param date date à convertir
     * @return String
     */
    public static String toString(LocalDateTime date) {
        if (date == null) {
            return EMPTY_STRING;
        }
        return toString(date, DEFAULT_PATTERN_TIME);
    }

    /**
     * Convertit une chaine de caractères en {@link LocalDate} avec le pattern passé
     * en paramètre (ex: dd/MM/yyyy)
     *
     * @param dateStr chaine de caractères à convertir
     * @param pattern pattern de conversion
     * @return String
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        if (dateStr.isEmpty() || pattern.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * Convertit une chaine de caractères en {@link LocalDate} avec le pattern par
     * défaut (dd/MM/yyyy).<br>
     * Si la chaine passée en paramètres est null alors la méthode retourne null
     *
     * @param dateStr chaine de caractères à convertir
     * @return String
     */
    public static LocalDate parseLocalDate(String dateStr) {
        if (dateStr.isEmpty()) {
            return null;
        }
        if (dateStr.contains("/")) {
            return parseLocalDate(dateStr, DEFAULT_PATTERN);
        } else if (dateStr.contains("-")) {
            return parseLocalDate(dateStr, MOMENT_PICKER_PATTERN);
        }
        return null;
    }

    /**
     * Convertit une chaine de caractères en {@link LocalDateTime} avec le pattern
     * passé en paramètre (ex: dd/MM/yyyy HH:mm:ss)
     *
     * @param dateStr chaine de caractères à convertir
     * @param pattern pattern de conversion
     * @return String
     */
    public static LocalDateTime parseLocalDateTime(String dateStr, String pattern) {
        if (dateStr.isEmpty() || pattern == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * Convertit une chaine de caractères en {@link LocalDateTime} avec le pattern
     * par défaut (dd/MM/yyyy HH:mm:ss)
     *
     * @param dateStr chaine de caractères à convertir
     * @return String
     */
    public static LocalDateTime parseLocalDateTime(String dateStr) {
        if (dateStr.isEmpty()) {
            return null;
        }
        return parseLocalDateTime(dateStr, DEFAULT_PATTERN_TIME);
    }

    /**
     * Retourne la liste des jours ouvrés entre 2 dates passées en paramètre
     *
     * @param dateDebut date de début
     * @param dateFin   date de fin
     * @param fermes    liste des jours fermés
     * @return List de {@link LocalDate}
     * @throws DateException en cas d'incohérence dans les dates (ex: datefin <
     *                       dateDebut)
     */
    public static List<LocalDate> getJoursOuvres(LocalDate dateDebut, LocalDate dateFin,
                                                 List<? extends Evenement> fermes) throws DateException {

        if (dateDebut == null) {
            throw new DateException("La date de début doit être non nulle.");
        }
        if (dateFin == null) {
            throw new DateException("La date de fin doit être non nulle.");
        }
        if (dateFin.isBefore(dateDebut)) {
            throw new DateException("La date de début doit être antérieure à la date de fin.");
        }

        List<LocalDate> joursOuvres = new ArrayList<>();
        joursOuvres.add(dateDebut);
        while (dateDebut.isBefore(dateFin)) {
            dateDebut = dateDebut.plus(1, ChronoUnit.DAYS);
            if (isOuvert(dateDebut, fermes)) {
                joursOuvres.add(dateDebut);
            }
        }
        return joursOuvres;
    }

    /**
     * Retourne la durée ouvrée entre 2 jours données, sachant que :
     * <ul>
     * <li>Si date début = date de fin, la durée retournée est égale à 1.</li>
     * <li>Si date de début ou date de fin est null, la durée retournée est 0.</li>
     * </ul>
     *
     * @param dateDebut date de début
     * @param dateFin   date de fin
     * @return int
     */
    public static int getDureeOuvree(LocalDate dateDebut, LocalDate dateFin) {

        if (dateDebut == null || dateFin == null) {
            return 0;
        }

        List<LocalDate> joursOuvres = new ArrayList<>();
        joursOuvres.add(dateDebut);
        while (dateDebut.isBefore(dateFin)) {
            dateDebut = dateDebut.plus(1, ChronoUnit.DAYS);
            if (isOuvert(dateDebut, new ArrayList<>())) {
                joursOuvres.add(dateDebut);
            }
        }
        return joursOuvres.size();
    }

    /**
     * Retourne la durée ouvrée entre 2 jours données, sachant que :
     * <ul>
     * <li>Si date début = date de fin, la durée retournée est égale à 1.</li>
     * <li>Si date de début ou date de fin est null, la durée retournée est 0.</li>
     * </ul>
     *
     * @param dateDebut date de début
     * @param dateFin   date de fin
     * @param fermes    liste des jours fermés
     * @return int
     * @throws DateException si les dates de début et de fin sont incohérentes
     */
    public static int getDureeOuvree(LocalDate dateDebut, LocalDate dateFin, List<? extends Evenement> fermes)
            throws DateException {

        List<LocalDate> dates = getJoursOuvres(dateDebut, dateFin, fermes);
        return dates.size();
    }

    /**
     * Retourne la durée en minutes entre 2 LocalDateTime
     * @param dateDebut date de début
     * @param dateFin   date de fin
     * @return long
     */
    public static long getDureeMinutes(LocalDateTime dateDebut, LocalDateTime dateFin) {

        if (dateDebut==null && dateFin==null) {
            return 0;
        }
        if (dateDebut==null && dateFin!=null) {
            return 1;
        }

        Duration duree = Duration.between(dateDebut, dateFin);
        return duree.toMinutes();
    }

    /**
     * Retourne le nb de jours ouvrés restant avant la date passée en paramètre. Si
     * la date passée en paramètre est dépassée ou nulle la méthode retourne -1
     *
     * @param date date
     * @return int
     */
    public static int getDelaiDemarrage(LocalDate date) {

        LocalDate now = LocalDate.now();
        if (date == null || date.isBefore(now)) {
            return -1;
        }

        if (now.equals(date)) {
            return 0;
        }

        return getDureeOuvree(now, date);
    }

    /**
     * Vérifie la cohérence de l'évènement en termes de dates
     *
     * @param dateDebut date de début
     * @param dateFin   date de fin
     * @throws DateException si une date est nulle ou si la date de fin n'est pas
     *                       supérieure ou égale à la date de début
     */
    public static void verifierEvenement(LocalDate dateDebut, LocalDate dateFin) throws DateException {
        if (dateDebut == null) {
            throw new DateException("La date de début doit être non nulle.");
        }
        if (dateFin == null) {
            throw new DateException("La date de fin doit être non nulle.");
        }
        if (dateFin.isBefore(dateDebut)) {
            throw new DateException("La date de début doit être antérieure à la date de fin.");
        }
    }

    /**
     * Vérifie la cohérence de l'évènement en termes de dates
     *
     * @param evenement évènement
     * @throws DateException si une date est nulle ou si la date de fin n'est pas
     *                       supérieure ou égale à la date de début
     */
    public static void verifierEvenement(Evenement evenement) throws DateException {
        verifierEvenement(evenement.getDateDebut(), evenement.getDateFin());
    }

    /**
     * Retourne true si 2 occupations contenus dans la liste se chevauchent et false
     * dans le cas contraire.
     *
     * @param liste1 liste d'évènements 1
     * @param liste2 liste d'évènements 2
     * @return boolean
     */
    public static boolean chevauchement(List<OccupationSalle> liste1, List<OccupationSalle> liste2) {
        for (OccupationSalle evt1 : liste1) {
            for (OccupationSalle evt2 : liste2) {
                if (evt1.isBesoinSalle() && evt2.isBesoinSalle() && evt1.getSalle() != null && evt2.getSalle() != null
                        && evt1.getSalle().equals(evt2.getSalle())) {
                    boolean result = DateUtils.chevauchement(evt1, evt2);
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Retourne true si 2 évènements se chevauchent et false dans le cas contraire.
     *
     * @param evt1 evènement 1
     * @param evt2 evènement 2
     * @return boolean
     */
    public static boolean chevauchement(PlageDate evt1, PlageDate evt2) {

        if (evt1==null || evt2==null) {
            return false;
        }

        boolean chevauchement = false;
        if (evt1.getDateDebut() == null || evt2.getDateDebut() == null) {
            chevauchement = false;
        } else if (evt1.getDateFin() == null || evt2.getDateFin() == null) {
            chevauchement = false;
        } else if (evt1.getDateDebut().equals(evt2.getDateDebut())) {
            chevauchement = true;
        } else if (evt1.getDateDebut().equals(evt2.getDateFin())) {
            chevauchement = true;
        } else if (evt1.getDateFin().equals(evt2.getDateDebut())) {
            chevauchement = true;
        } else if (evt1.getDateFin().equals(evt2.getDateFin())) {
            chevauchement = true;
        } else if (evt1.getDateDebut().isAfter(evt2.getDateDebut())
                && evt1.getDateDebut().isBefore(evt2.getDateFin())) {
            chevauchement = true;
        } else if (evt1.getDateFin().isAfter(evt2.getDateDebut()) && evt1.getDateFin().isBefore(evt2.getDateFin())) {
            chevauchement = true;
        } else if (evt1.getDateDebut().isAfter(evt2.getDateDebut()) && evt1.getDateFin().isBefore(evt2.getDateFin())) {
            chevauchement = true;
        } else if (evt2.getDateDebut().isAfter(evt1.getDateDebut()) && evt2.getDateFin().isBefore(evt1.getDateFin())) {
            chevauchement = true;
        }

        return chevauchement;
    }

    public static boolean chevauchement(PlageDate evt, LocalDate date) {
        boolean chevauchement = false;
        if (evt.getDateDebut().equals(date)) {
            chevauchement = true;
        }
        else if (evt.getDateDebut().isBefore(date) && evt.getDateFin()==null) {
            chevauchement = true;
        }
        else if (evt.getDateFin()!=null && evt.getDateFin().equals(date)) {
            chevauchement = true;
        } else if (evt.getDateFin()!=null && evt.getDateDebut().isBefore(date) && evt.getDateFin().isAfter(date)) {
            chevauchement = true;
        }
        return chevauchement;
    }

    /**
     * Retourne true si l'évènement passé en paramètre chevauche la journée passée
     * en paramètre
     *
     * @param evt  evènement
     * @param jour jour
     * @return boolean
     */
    public static boolean chevauchement(PlageDate evt, Jour jour) {
        return chevauchement(evt, jour.getDate());
    }

    /**
     * Retourne true si le second évènement passé en paramètre ne chevauche aucun
     * des évènements de la liste en paramètre
     *
     * @param events   évènements
     * @param newEvent nouvel évènement
     * @return boolean
     */
    public static boolean chevauchement(List<? extends PlageDate> events, PlageDate newEvent) {
        for (PlageDate event : events) {
            if (DateUtils.chevauchement(event, newEvent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calcule la date de début d'une liste de plage de dates: on prend le minimum
     * des dates de débuts. Si la liste est vide, cette méthode retourne null.
     *
     * @return {@link LocalDate} ou null
     */
    public static LocalDate calculerDateDebut(List<? extends PlageDate> listePlages) {
        LocalDate dateDebut = null;
        for (PlageDate evt : listePlages) {
            if (dateDebut == null || evt.getDateDebut().isBefore(dateDebut)) {
                dateDebut = evt.getDateDebut();
            }
        }

        return dateDebut;
    }

    /**
     * Calcule la date de fin d'une liste de plages de dates: on prend le maximum
     * des dates de fins. Si la liste est vide, cette méthode retourne null.
     *
     * @param listePlages liste de plages de dates
     * @return {@link LocalDate} ou null
     */
    public static LocalDate calculerDateFin(List<? extends PlageDate> listePlages) {
        LocalDate dateFin = null;
        for (PlageDate plage : listePlages) {
            if (dateFin == null || plage.getDateFin().isAfter(dateFin)) {
                dateFin = plage.getDateFin();
            }
        }

        return dateFin;
    }

    /**
     * Convertit une java.sql.Date en {@link LocalDate}
     *
     * @param date date à convertir
     * @return {@link LocalDate}
     */
    public static LocalDate toLocalDate(Date date) {
        if (date==null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Convertit un java.sql.Timestamp en {@link LocalDateTime}
     *
     * @param timestamp timestamp à convertir
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp==null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);

        return LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    /**
     * Méthode qui créée la semaine correspondant à une date donnée.
     *
     * @param cours     cours
     * @param fermes    liste de période fermées
     * @return {@link Semaine}
     * @throws DateException en cas d'incohérence dans les dates
     */
    public static Semaine creerSemaine(LocalDate date, List<CoursPlanifie> cours, List<Evenement> fermes)
            throws DateException {

        LocalDate dateDebut = date.with(ChronoField.DAY_OF_WEEK, 1);
        LocalDate dateFin = date.with(ChronoField.DAY_OF_WEEK, 5);

        Calendrier cal = new Calendrier(dateDebut, dateFin, cours, fermes);
        return cal.getSemaines().get(0);
    }

    /**
     * Méthode qui créée la liste des semaines entre 2 dates données. Attention les
     * 2 dates doivents forcément être des jours ouvrés.
     *
     * @param dateDebut date de début
     * @param dateFin   date de fin
     * @param cours     cours
     * @param fermes    liste de période fermées
     * @return List de Semaine
     * @throws DateException en cas d'incohérence dans les dates
     */
    public static List<Semaine> creerSemaines(LocalDate dateDebut, LocalDate dateFin, List<CoursPlanifie> cours,
                                              List<Evenement> fermes) throws DateException {

        if (!isOuvert(dateDebut, fermes)) {
            throw new DateException(
                    "La date de début de la session n'est pas une date ouverte :" + DateUtils.toString(dateDebut));
        }

        Calendrier cal = new Calendrier(dateDebut, dateFin, cours, fermes);
        return cal.getSemaines();
    }

    /**
     * Retourne la liste des mois pour la plage de date passée en paramètre. Les
     * mois sont retournés au format "Jan. 2019".
     *
     * @param plageDate plage de date
     * @return List de {@link LocalDate}
     */
    public static List<Mois> creerMois(PlageDate plageDate) {

        List<Mois> mois = new ArrayList<>();

        LocalDate curDate = plageDate.getDateDebut();
        while (curDate.isBefore(plageDate.getDateFin()) || curDate.equals(plageDate.getDateFin())) {

            LocalDate premiersDuMois = LocalDate.of(curDate.getYear(), curDate.getMonth(), 1);

            String nomMois = premiersDuMois.format(DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE)).toUpperCase();
            int nbLettres = 4;
            if (nomMois.length() < 4) {
                nbLettres = 3;
            }
            mois.add(new Mois(nomMois.substring(0, nbLettres) + ". " + curDate.getYear(),
                    curDate.get(ChronoField.MONTH_OF_YEAR)));
            curDate = curDate.plus(1, ChronoUnit.MONTHS);
        }
        return mois;
    }

    /**
     * Retourne le numéro de semaine pour la date passée en paramètre
     *
     * @param date date
     * @return int
     */
    public static int getNumeroSemaine(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    /**
     * Retourne le libellé court (3 caractères) du jour passé en paramètre
     * ex:
     * La méthode retourne soit Lun, soit Mar, soit Mer, etc.. selon le jour de la semaine passé en paramètre
     *
     * @param date date
     * @return String
     */
    public static String getLibelleCourt(LocalDate date) {
        if (date==null) {
            return null;
        }
        String lib = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.FRANCE);
        return lib.substring(0, 1).toUpperCase()+lib.substring(1, 3);
    }

    public static Boolean chevauchement(TjmSociete t, LocalDate date) {
        return null;
    }

    /**
     * Représente un mois du calendrier
     */
    public static class Mois {
        /** nom */
        private String nom;
        /** numero */
        private int numero;

        /**
         * Constructeur
         *
         * @param nom    libellé
         * @param numero numéro
         */
        public Mois(String nom, int numero) {
            super();
            this.nom = nom;
            this.numero = numero;
        }

        /**
         * Getter
         *
         * @return the nom
         */
        public String getNom() {
            return nom;
        }

        /**
         * Setter
         *
         * @param nom the nom to set
         */
        public void setNom(String nom) {
            this.nom = nom;
        }

        /**
         * Getter
         *
         * @return the numero
         */
        public int getNumero() {
            return numero;
        }

        /**
         * Setter
         *
         * @param numero the numero to set
         */
        public void setNumero(int numero) {
            this.numero = numero;
        }

    }

    /**
     * Représente une plage de date délimitée par une date de début et une date de
     * fin
     *
     * @author DIGINAMIC
     *
     */
    public static class PlageDateImpl implements PlageDate {

        /** dateDebut : LocalDate */
        protected LocalDate dateDebut;

        /** dateFin : LocalDate */
        protected LocalDate dateFin;

        /**
         * Constructeur
         *
         * @param dateDebut date de début
         * @param dateFin   date de fin
         */
        public PlageDateImpl(LocalDate dateDebut, LocalDate dateFin) {
            super();
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
        }

        /**
         * Calcule les nouvelles bornes de la plage de dates courante à partir de celle
         * passée en paramètre
         *
         * @param plage plage de dates
         */
        public void modifieBornes(PlageDate plage) {
            if (plage == null) {
                return;
            }
            if (dateDebut == null) {
                dateDebut = plage.getDateDebut();
                dateFin = plage.getDateFin();
            }
            if (plage.getDateDebut() != null && plage.getDateDebut().isBefore(dateDebut)) {
                dateDebut = plage.getDateDebut();
            }
            if (plage.getDateFin() != null && plage.getDateFin().isAfter(dateFin)) {
                dateFin = plage.getDateFin();
            }
        }

        /**
         * Getter
         *
         * @return the dateDebut
         */
        @Override
        public LocalDate getDateDebut() {
            return dateDebut;
        }

        /**
         * Setter
         *
         * @param dateDebut the dateDebut to set
         */
        public void setDateDebut(LocalDate dateDebut) {
            this.dateDebut = dateDebut;
        }

        /**
         * Getter
         *
         * @return the dateFin
         */
        @Override
        public LocalDate getDateFin() {
            return dateFin;
        }

        /**
         * Setter
         *
         * @param dateFin the dateFin to set
         */
        public void setDateFin(LocalDate dateFin) {
            this.dateFin = dateFin;
        }

    }

    /**
     * Calcule les dates de fin de mois comprises entre la date de début et la date
     * de fin. La valeur du delta (positif ou négatif) permet d'ajouter ou
     * retrancher un certain nombre de jours à chacune de ces fins de mois
     *
     * @param dateDebut date de début
     * @param dateFin   date de fin
     * @param delta     valeur à ajouter ou retrancher à chaque date calculée
     * @return List de {@link LocalDate}
     */
    public static List<LocalDate> getDatesFinMois(LocalDate dateDebut, LocalDate dateFin, int delta) {

        List<LocalDate> dates = new ArrayList<>();

        LocalDate cursor = dateDebut;
        while (cursor.isBefore(dateFin)) {
            cursor = cursor.with(TemporalAdjusters.lastDayOfMonth());
            LocalDate dateFinAjustee = cursor.plus(delta, ChronoUnit.DAYS);

            dates.add(dateFinAjustee);

            cursor = cursor.plus(1, ChronoUnit.DAYS);
        }

        return dates;
    }

    /**
     * Retourne si oui ou non la chaine passée en paramètre contient une date au
     * format précisé par la variable template
     *
     * @param dateDebutStr chaine de caractères contenant une date
     * @param template     format de la date (ex: yyyy-MM-dd)
     * @return boolean
     */
    public static boolean isDate(String dateDebutStr, String template) {
        if (dateDebutStr.isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(dateDebutStr, DateTimeFormatter.ofPattern(template));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /** Retourne l'age d'une personne à partir de sa date de naissance
     * @param dateNaissance date de naissance
     * @return int
     */
    public static int getAge(LocalDate dateNaissance) {
        LocalDate jour = LocalDate.now();
        Period period = Period.between(dateNaissance, jour);
        return period.getYears();
    }

}
