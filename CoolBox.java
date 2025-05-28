import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Random;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CoolBox {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    String[] apple   = {"Iphone 12", "Iphone 14", "Iphone 15", "Iphone 16e", "Iphone 16 promax"};
    double[] precioApple = {2039, 2869, 3399, 3589, 5399};
    String[] samsung = {"Samsung Galaxy S23 Ultra", "Samsung Galaxy S24 Ultra",
            "Samsung Galaxy S25 Ultra", "Samsung Z Fold"};
    double[] precioSamsung = {3839, 4199, 4799, 6739};
    String[] xiaomi  = {"Xiaomi Poco F6", "Xiaomi Redmi Note 14 Pro Plus",
            "Xiaomi Mi 12 Pro", "Xiaomi 14T Pro"};
    double[] precioXiaomi = {1699, 1899, 2339, 3049};
    String[] ZTE ={"ZTE Blade A35","ZTE A35e","ZTE A55","ZTE Blade V60 Smart","ZTE Blade V50 Vita"};
    double[] precioZTE ={225,279,329,389,489};
    String[] Honor ={"Honor X5b","Honor X5b Plus","Honor X6a","Honor X7C","Honor X5 Plus"};
    double[] precioHonor ={409,549,625,739,840};
    String[] Huawei ={"Huawei Nova 10 SE","Huawei Nova 13","Huawei Nova 13 Pro","Huawei Pura 70","Huawei Pura 70 Ultra"};
    double[] precioHuawei ={999,1649,1949,2299,3999};
    final double IGV = 0.18;
    private String emailGuardado;
    private String passwordGuardado;

    // Fecha límite de la promoción
    private final LocalDate fechaLimitePromo = LocalDate.of(2025, 5, 30);
    private double ultimoVuelto = 0.0;

    private boolean validarCorreo(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.com$";
        return Pattern.compile(regex).matcher(email).matches();
    }
    private boolean validarPassword(String password) {
        String regex =
                "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }
    public void registrarCuenta() {
        System.out.println("\n---- REGISTRO DE CUENTA ----");
        String email, password;

        do {
            System.out.print("Ingrese su correo electrónico: ");
            email = scanner.nextLine();
            if (!validarCorreo(email)) {
                System.out.println(
                        "❌ Correo inválido. Debe contener '@' y terminar en '.com'."
                );
            }
        } while (!validarCorreo(email));

        do {
            System.out.print(
                    "Ingrese su contraseña (mínimo 8 caracteres, debe incluir letras, números y caracteres especiales): "
            );
            password = scanner.nextLine();
            if (!validarPassword(password)) {
                System.out.println(
                        "❌ Contraseña inválida. Debe incluir letras, números y caracteres especiales."
                );
            }
        } while (!validarPassword(password));

        emailGuardado = email;
        passwordGuardado = password;
        System.out.println("✅ Registro exitoso. Ahora puede iniciar sesión.");
    }
    public boolean iniciarSesion() {
        System.out.println("\n---- INICIO DE SESIÓN ----");
        int intentos = 3;
        while (intentos > 0) {
            System.out.print("Ingrese su correo: ");
            String emailIngresado = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String passwordIngresado = scanner.nextLine();

            if (
                    emailIngresado.equals(emailGuardado) &&
                            passwordIngresado.equals(passwordGuardado)
            ) {
                System.out.println("✅ Inicio de sesión exitoso. Bienvenido!");
                return true;
            } else {
                intentos--;
                System.out.println(
                        "❌ Correo o contraseña incorrectos. Intentos restantes: " + intentos
                );
            }
        }
        System.out.println("⛔ Se agotaron los intentos de inicio de sesión.");
        return false;
    }
    private void ordenarAsc(String[] n, double[] p, int len) {
        for (int i = 0; i < len - 1; i++)
            for (int j = 0; j < len - 1 - i; j++)
                if (p[j] > p[j + 1]) intercambiar(n, p, j, j + 1);
    }
    private void ordenarDesc(String[] n, double[] p, int len) {
        for (int i = 0; i < len - 1; i++)
            for (int j = 0; j < len - 1 - i; j++)
                if (p[j] < p[j + 1]) intercambiar(n, p, j, j + 1);
    }
    private void intercambiar(String[] n, double[] p, int i, int j) {
        String tmpN = n[i]; n[i] = n[j]; n[j] = tmpN;
        double tmpP = p[i]; p[i] = p[j]; p[j] = tmpP;
    }
    private void listar(String[] n, double[] p, int len) {
        for (int i = 0; i < len; i++)
            System.out.printf("• %-35s S/ %.2f%n", n[i], p[i]);
        System.out.println();
    }
    private int copiar(String[] srcN, double[] srcP,
                       String[] dstN, double[] dstP, int pos) {
        for (int i = 0; i < srcN.length; i++, pos++) {
            dstN[pos] = srcN[i];
            dstP[pos] = srcP[i];
        }
        return pos;
    }
    public void menuPrincipal() {
        int op;
        do {
            System.out.println("\n=== COOLBOX ===");
            if (!LocalDate.now().isAfter(fechaLimitePromo)) {
                long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaLimitePromo);
                System.out.printf("🎁 ¡Promoción activa! Hasta el %s puedes obtener hasta 50%% de descuento. (%d días restantes)%n",
                        fechaLimitePromo, diasRestantes);
            }
            System.out.println("1. Celulares");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            op = scanner.nextInt(); scanner.nextLine();
            if (op == 1) menuCelulares();
        } while (op != 0);
    }
    private void menuCelulares() {
        System.out.println("\n--- CELULARES ---");
        System.out.println("1. Mostrar TODO");
        System.out.println("2. Filtrar por MARCA");
        System.out.println("3. Filtrar por PRECIO");
        System.out.println("4. Comprar producto");
        System.out.print("Opción: ");
        int sub = scanner.nextInt(); scanner.nextLine();
        switch (sub) {
            case 1 -> mostrarTodo();
            case 2 -> filtrarPorMarca();
            case 3 -> filtrarPorPrecio();
            case 4 -> procesarCompra();
            default -> System.out.println("Opción inválida.\n");
        }
    }
    private void mostrarTodo() {
        String[] nombres = new String[apple.length + samsung.length + xiaomi.length+ZTE.length+Honor.length+Huawei.length];
        double[] precios = new double[nombres.length];
        int len = 0;
        len = copiar(apple, precioApple, nombres, precios, len);
        len = copiar(samsung, precioSamsung, nombres, precios, len);
        len = copiar(xiaomi, precioXiaomi, nombres, precios, len);
        len = copiar(ZTE,precioZTE, nombres,precios, len);
        len = copiar(Honor,precioHonor, nombres,precios,len);
        copiar(Huawei,precioHuawei, nombres,precios,len);
        char orden = pedirOrden();
        if (orden == 'a') ordenarAsc(nombres, precios, nombres.length);
        else if (orden == 'd') ordenarDesc(nombres, precios, nombres.length);
        System.out.println("\n📱 Lista de celulares:");
        listar(nombres, precios, nombres.length);
    }
    private void filtrarPorMarca() {
        System.out.print("Marca (Apple / Samsung / Xiaomi / ZTE / Honor / Huawei): ");
        String m = scanner.nextLine().toLowerCase();
        String[] nombres; double[] precios;
        switch (m) {
            case "apple"   -> { nombres = apple;   precios = precioApple;   }
            case "samsung" -> { nombres = samsung; precios = precioSamsung; }
            case "xiaomi"  -> { nombres = xiaomi;  precios = precioXiaomi;  }
            case "zte" -> {nombres = ZTE; precios = precioZTE; }
            case "honor" -> {nombres = Honor; precios =precioHonor;}
            case "huawei" -> {nombres = Huawei;precios = precioHuawei;}
            default -> { System.out.println("Marca no encontrada.\n"); return; }
        }
        String[] tempN = nombres.clone();
        double[] tempP = precios.clone();
        char orden = pedirOrden();
        if (orden == 'a') ordenarAsc(tempN, tempP, tempN.length);
        else if (orden == 'd') ordenarDesc(tempN, tempP, tempN.length);
        System.out.println("\n📱 " + m.substring(0,1).toUpperCase()+m.substring(1)+" disponibles:");
        listar(tempN, tempP, tempN.length);
    }
    private void filtrarPorPrecio() {
        System.out.print("Precio mínimo: ");
        double min = scanner.nextDouble();
        System.out.print("Precio máximo: ");
        double max = scanner.nextDouble(); scanner.nextLine();
        String[] todosN = new String[apple.length + samsung.length + xiaomi.length+ZTE.length+Honor.length+ Huawei.length];
        double[] todosP = new double[todosN.length];
        int len = 0;
        len = copiar(apple, precioApple, todosN, todosP, len);
        len = copiar(samsung, precioSamsung, todosN, todosP, len);
        len = copiar(xiaomi, precioXiaomi, todosN, todosP, len);
        len = copiar(ZTE, precioZTE, todosN, todosP, len);
        len = copiar(Honor, precioHonor, todosN, todosP, len);
        copiar(Huawei, precioHuawei, todosN, todosP, len);
        String[] filtradosN = new String[todosN.length];
        double[] filtradosP = new double[todosN.length];
        int nFil = 0;
        for (int i = 0; i < todosN.length; i++) {
            if (todosP[i] >= min && todosP[i] <= max) {
                filtradosN[nFil] = todosN[i];
                filtradosP[nFil] = todosP[i];
                nFil++;
            }
        }
        if (nFil == 0) {
            System.out.println("\nSin resultados en ese rango.\n");
            return;
        }
        char orden = pedirOrden();
        if (orden == 'a') ordenarAsc(filtradosN, filtradosP, nFil);
        else if (orden == 'd') ordenarDesc(filtradosN, filtradosP, nFil);
        System.out.println("\n📱 Resultados (" + min + " - " + max + "):");
        listar(filtradosN, filtradosP, nFil);
    }
    private char pedirOrden() {
        System.out.print("Ordenar por precio (a = asc / d = desc / n = sin ordenar): ");
        char c = scanner.nextLine().toLowerCase().charAt(0);
        return (c == 'a' || c == 'd') ? c : 'n';
    }
    private void procesarCompra() {
        System.out.print("\nNombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("DNI (8 dígitos): ");
        String dni;
        do {
            dni = scanner.nextLine();
            if (!dni.matches("\\d{8}")) {
                System.out.print("❌ DNI inválido. Debe tener exactamente 8 dígitos. Intente nuevamente: ");
            }
        } while (!dni.matches("\\d{8}"));
        System.out.println("\nElija la MARCA (Apple / Samsung / Xiaomi / ZTE / Honor / Huawei): ");
        String marca = scanner.nextLine().toLowerCase();
        String[] nombres;  double[] precios;
        switch (marca) {
            case "apple"   -> { nombres = apple;   precios = precioApple; }
            case "samsung" -> { nombres = samsung; precios = precioSamsung; }
            case "xiaomi"  -> { nombres = xiaomi;  precios = precioXiaomi; }
            case "zte" -> {nombres = ZTE; precios = precioZTE; }
            case "honor" -> {nombres = Honor; precios =precioHonor;}
            case "huawei" -> {nombres = Huawei;precios = precioHuawei;}
            default -> { System.out.println("Marca no válida."); return; }
        }
        System.out.println("Productos disponibles:");
        for (int i = 0; i < nombres.length; i++)
            System.out.printf("%d) %-35s S/ %.2f%n", i + 1, nombres[i], precios[i]);
        System.out.print("Seleccione número de producto: ");
        int idx = scanner.nextInt() - 1; scanner.nextLine();
        if (idx < 0 || idx >= nombres.length) {
            System.out.println("Selección inválida."); return;
        }
        String producto = nombres[idx];
        double precioOriginal = precios[idx];
        double descuento = obtenerDescuento();
        double precioFinal = precioOriginal * (1 - descuento);

        if (descuento > 0) {
            System.out.printf("🎉 ¡Promoción aplicada! Descuento del %.0f%%. Nuevo precio: S/ %.2f%n",
                    descuento * 100, precioFinal);
        }

        System.out.println("\nMétodo de pago:");
        System.out.println("1. Tarjeta");
        System.out.println("2. Efectivo");
        System.out.print("Elija: ");
        int pago = scanner.nextInt(); scanner.nextLine();
        boolean pagoOk = switch (pago) {
            case 1 -> pagarConTarjeta(precioFinal);
            case 2 -> pagarEnEfectivo(precioFinal);
            default -> { System.out.println("Opción de pago inválida."); yield false; }
        };
        if (pagoOk) imprimirBoleta(nombre, dni, producto, precioOriginal, descuento);
        else        System.out.println("❌  Compra cancelada.\n");
    }
    private double obtenerDescuento() {
        if (LocalDate.now().isAfter(fechaLimitePromo)) return 0.0;
        int[] opciones = {15, 25, 50};
        int porcentaje = opciones[random.nextInt(opciones.length)];
        return porcentaje / 100.0;
    }
    private boolean pagarConTarjeta(double precio) {
        System.out.println("\n--- Pago con tarjeta ---");
        System.out.print("Número de tarjeta (16 dígitos): ");
        String num = scanner.nextLine();
        if (!num.matches("\\d{16}")) {
            System.out.println("Número inválido."); return false;
        }
        System.out.print("CVV (3 dígitos): ");
        String cvv = scanner.nextLine();
        if (!cvv.matches("\\d{3}")) {
            System.out.println("CVV inválido."); return false;
        }
        System.out.printf("✔ Pago de S/ %.2f autorizado.%n", precio);
        ultimoVuelto = 0.0;
        return true;
    }
    public boolean pagarEnEfectivo(double precio) {
        System.out.println("\n--- Pago en efectivo ---");
        System.out.printf("Total a pagar: S/ %.2f%n", precio);
        System.out.print("Ingrese monto entregado: ");
        double monto = scanner.nextDouble(); scanner.nextLine();
        if (monto < precio) {
            System.out.println("Monto insuficiente."); return false;
        }
        double vuelto = monto - precio;
        System.out.printf("✔ Pago aceptado. Vuelto: S/ %.2f%n", vuelto);
        ultimoVuelto = vuelto;
        return true;
    }
    public void imprimirBoleta(String nombre, String dni,
                               String producto, double precioOriginal, double descuento) {
        double precioConDescuento = precioOriginal * (1 - descuento);
        double igv = precioConDescuento * IGV;
        double subtotal = precioConDescuento - igv;
        double total = precioConDescuento;

        System.out.println("\n========= BOLETA DE VENTA =========");
        System.out.printf("Cliente : %s%n", nombre);
        System.out.printf("DNI     : %s%n", dni);
        System.out.println("-----------------------------------");
        System.out.printf("Producto: %s%n", producto);
        System.out.printf("Precio original : S/ %.2f%n", precioOriginal);
        if (descuento > 0) {
            System.out.printf("Descuento aplicado: %.0f%%%n", descuento * 100);
        }
        System.out.printf("Precio final    : S/ %.2f%n", precioConDescuento);
        System.out.printf("IGV 18%%         : S/ %.2f%n", igv);
        System.out.printf("SUBTOTAL        : S/ %.2f%n", subtotal);
        System.out.printf("TOTAL           : S/ %.2f%n", total);
        if (ultimoVuelto > 0) {
            System.out.printf("VUELTO          : S/ %.2f%n", ultimoVuelto);
        }
        System.out.println("===================================\n");
    }
    public static void main(String[] args) {
        CoolBox cb = new CoolBox();
        cb.registrarCuenta();
        if (cb.iniciarSesion()) cb.menuPrincipal();
        else System.out.println("Acceso denegado.");
    }
}
