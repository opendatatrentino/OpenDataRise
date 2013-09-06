/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import com.google.refine.ProjectManager;
import com.google.refine.model.Project;
import com.google.refine.preference.PreferenceStore;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Locale;

/**
 *
 * @author David Leoni
 */
public class ODR {

    public static final int MAX_STEPS = 8;
    public static final Logger logger = LoggerFactory.getLogger("odr");
    public static HashMap<Label, String> labelDict = new HashMap<Label, String>();
    public static String PROJECT_OVERLAY_NAME = "OdrProjectOverlay";

    public static final String getLabelString(Label label) {
        return labelDict.get(label);
    }

    /**
     * Java 7 has  Locale.forLanguageTag(format), this is the substitute for Java 6
     */
    static public Locale languageTagToLocale(String languageTag) {
        
        int c = languageTag.indexOf('_');
        if (c > 0) {
            return new Locale(languageTag.substring(0, c), languageTag.substring(c + 1));
        } else {
            return new Locale(languageTag);
        }
        
    }

    /**
     * Locale is actually set in Refine preferences as an array. Here we return
     * the favourite locale.
     *
     * @return
     */
    static public Locale getLocale() {

        String[] langs = {"default"};
        PreferenceStore ps = ProjectManager.singleton.getPreferenceStore();
        if (ps != null) {
            langs = new String[]{(String) ps.get("userLang")};
        }

        langs = Arrays.copyOf(langs, langs.length + 1);
        langs[langs.length - 1] = "default";

        if (langs[0].equals("default")) {
            return Locale.ENGLISH;
        } else {
            return ODR.languageTagToLocale(langs[0]);
        }
    }

    public static final Label getStepLabel(int step) {
        return Label.values()[step];
    }

    public static final String getStepName(int step) {
        return getLabelString(getStepLabel(step));
    }

    public static final void init() {
        labelDict.put(Label.SELECTION, "Selection");
        labelDict.put(Label.SCHEMA_MATCHING, "Schema matching");
        labelDict.put(Label.DATA_VALIDATION, "Data Validation");
        labelDict.put(Label.SEMANTIC_ENRICHMENT, "Enrichment");
        labelDict.put(Label.RECONCILIATION, "Reconciliation");
        labelDict.put(Label.EXPORTING, "Exporting");
        labelDict.put(Label.PUBLISHING, "Publishing");

    }

    public static void initOverlay(Project prj) {

        OdrProjectOverlay po = new OdrProjectOverlay();
        po.setProject(prj);
        prj.overlayModels.put(ODR.PROJECT_OVERLAY_NAME, po);

    }
}