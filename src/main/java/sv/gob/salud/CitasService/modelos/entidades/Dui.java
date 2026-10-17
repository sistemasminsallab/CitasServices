package sv.gob.salud.CitasService.modelos.entidades;

import java.util.regex.Pattern;

/** Reglas del DUI en un solo lugar (SRP): Persona ya no sabe cómo se valida. */
public final class Dui {
    private static final Pattern FORMATO = Pattern.compile("^\\d{8}-\\d$");
    private static final String CEROS = "00000000-0";

    private Dui() { }

    public static boolean esValido(String dui) {
        if (dui == null || !FORMATO.matcher(dui.trim()).matches() || CEROS.equals(dui.trim())) {
            return false;
        }
        String[] partes = dui.trim().split("-");
        int suma = 0;
        for (int i = 0; i < 8; i++) {
            suma += Character.getNumericValue(partes[0].charAt(i)) * (9 - i);
        }
        int esperado = (10 - suma % 10) % 10;
        return esperado == Integer.parseInt(partes[1]);
    }
}
