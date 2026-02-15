import java.sql.*;

// Escribir la libreira;  // importar la libreria

public class Main {
    public static void main(String[] args) {
        // 1. Variables de conexion
        String url = "jdbc:mysql://localhost:3306/Sena";
        String user = "root";
        String password = "Facosta608*";

        Connection conexion = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 2. Cargar Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. Establecer conexion
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion establecida con la base de datos Sena.");

            // --- PARTE 1: INSERTAR NUEVO REGISTRO ---
            String sqlInsert = "INSERT INTO Medico (tarjeta_profesional, nombre_1, nombre_2, apellido_1, apellido_2, correo) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conexion.prepareStatement(sqlInsert);

            // Datos de ejemplo (Asegurate que el ID no este repetido)
            pstmt.setInt(1, 98765);
            pstmt.setString(2, "Elena");
            pstmt.setString(3, "Maria");
            pstmt.setString(4, "Lopez");
            pstmt.setString(5, "Castro");
            pstmt.setString(6, "elena.lopez@email.com");

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Insercion exitosa del medico.");
            }

            // --- PARTE 2: LEER Y MOSTRAR TODOS LOS REGISTROS ---
            System.out.println("\n--- LISTADO ACTUALIZADO DE MEDICOS ---");
            String sqlSelect = "SELECT * FROM Medico";
            stmt = conexion.createStatement();
            rs = stmt.executeQuery(sqlSelect);

            // El bucle while recorre mientras haya registros (rs.next() sea true)
            while (rs.next()) {
                System.out.println("Tarjeta: " + rs.getInt("tarjeta_profesional") +
                        " | Nombre: " + rs.getString("nombre_1") + " " + rs.getString("apellido_1") +
                        " | Email: " + rs.getString("correo"));
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        } finally {
            // 4. Cerrar recursos en orden inverso a su apertura
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (pstmt != null) pstmt.close();
                if (conexion != null) conexion.close();
                System.out.println("\nRecursos cerrados correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

