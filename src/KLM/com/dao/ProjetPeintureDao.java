package KLM.com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import KLM.com.model.ProjetPeinture;
import KLM.com.model.Rouleaux;
import KLM.com.model.*;
import KLM.com.util.DbUtil;
import KLM.com.util.HibernateUtil;
import KLM.com.util.CreateConnection;

public class ProjetPeintureDao {

	

	public String displayProjet(ProjetPeinture project, Rouleaux roll, SousCouche undercoat, Adhesif stripe,
			Produits peinture) {

		String test = "";

		int idProjetPeinture = project.getIdProjetPeinture();

		Connection con = null;
		Statement st = null;
		Statement st2 = null;
		Statement st3 = null;
		Statement st4 = null;
		Statement st5 = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) CreateConnection.createConnection();

			String sql = "SELECT * FROM projetPeinture WHERE idProjetPeinture = '" + idProjetPeinture + "'";
			st = (Statement) con.createStatement();

			ResultSet result = (ResultSet) st.executeQuery(sql);

			while (result.next()) {

				int idProjet = result.getInt("idProjet");
				int idRouleaux = result.getInt("idRouleaux");
				int idAdhesif = result.getInt("idAdhesif");
				int idUndercoat = result.getInt("idUndecoat");
				int idPeinture = result.getInt("idPeinture");

				project.setIdProjet(idProjet);

				project.setIdRouleaux(idRouleaux);
				project.setIdAdhesif(idAdhesif);
				project.setIdUndercoat(idUndercoat);
				project.setIdPeinture(idPeinture);

				String sql2 = "SELECT * FROM rouleaux WHERE idRouleaux = '" + idRouleaux + "'";
				st2 = (Statement) con.createStatement();
				ResultSet result2 = (ResultSet) st.executeQuery(sql2);
				while (result2.next()) {

					roll.setImgR(result2.getString("imgR"));
					roll.setLienR(result2.getString("lienR"));
					roll.setTypeRouleau(result2.getString("TypeRouleau"));
					roll.setPrix(result2.getDouble("prix"));
					roll.setNomRouleau(result2.getString("nomRouleau"));

				}
				String sql3 = "SELECT * FROM Peinture WHERE idProduit = '" + idPeinture + "'";
				st3 = (Statement) con.createStatement();
				ResultSet result3 = (ResultSet) st.executeQuery(sql3);
				while (result3.next()) {

					peinture.setImage(result3.getString("image"));
					peinture.setLien(result3.getString("lien"));
					peinture.setNomProduit(result3.getString("nomProduit"));
					peinture.setPrix(result3.getDouble("prix"));
					peinture.setCouleur(result3.getString("couleur"));
					peinture.setContenance(result3.getDouble("contenance"));

				}
				String sql4 = "SELECT * FROM adhesif WHERE idAdhesif = '" + idAdhesif + "'";
				st4 = (Statement) con.createStatement();
				ResultSet result4 = (ResultSet) st.executeQuery(sql4);
				while (result4.next()) {

					stripe.setImgA(result4.getString("imgA"));
					stripe.setLienA(result4.getString("lienA"));
					stripe.setNomA(result4.getString("nomA"));
					stripe.setPrix(result4.getDouble("prix"));
				}
				String sql5 = "SELECT * FROM sousCouche WHERE idSousCouche = '" + idUndercoat + "'";
				st5 = (Statement) con.createStatement();
				ResultSet result5 = (ResultSet) st.executeQuery(sql5);
				while (result5.next()) {

					undercoat.setImgSC(result5.getString("imgSC"));
					undercoat.setLienSC(result5.getString("lienSC"));
					undercoat.setNomSC(result5.getString("nomSC"));
					undercoat.setPrix(result5.getDouble("prix"));
				}

				test = "SUCCESS";
				System.out.println("ok");

			}

			return test;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;

	}

	public String createNewProjetPeinture(ProjetPeinture project, HttpSession session) {

		Connection con = null;
		PreparedStatement pst = null;
		Statement st = null;

		String test = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) CreateConnection.createConnection();

			
			String query = "insert into projetPeinture(idRouleaux, idAdhesif, idUndecoat) values (?,?,?)";

			pst = con.prepareStatement(query);

			pst.setInt(1, 335307);
			pst.setInt(2, 713473);
			pst.setInt(3, 3989626);

			session.setAttribute("idUndercoat", 3989626);
			session.setAttribute("idAdhesif", 713473);
			session.setAttribute("idRouleaux", 335307);

			System.out.println();
			// session.setAttribute("idProjet", id);

			int i = pst.executeUpdate();

			String sql = "SELECT * FROM projetPeinture ORDER BY idProjetPeinture DESC LIMIT 1";
			st = (Statement) con.createStatement();

			ResultSet result = (ResultSet) st.executeQuery(sql);

			while (result.next()) {

				int a = result.getInt("idProjetPeinture");
				project.setIdProjetPeinture(a);
				session.setAttribute("idP", a);

			}

			if (i != 0) { // Just to ensure data has been inserted into the database

				project.setIdAdhesif(713473);
				project.setIdRouleaux(335307);
				project.setIdUndercoat(3989626);

				return "SUCCESS";
			} else {
				System.out.println("Something went wrong...");
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;

	}

	public String addRoom(int a, String room) {

		Connection con = null;
		PreparedStatement pst = null;

		String test = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = (Connection) CreateConnection.createConnection();

			String query = "UPDATE projetPeinture SET nomPièce = '" + room + "' WHERE idProjetPeinture = '" + a + "'";

			pst = con.prepareStatement(query); // Making use of prepared statements here to insert bunch
												// of data


			int i = pst.executeUpdate();

			if (i != 0) { // Just to ensure data has been inserted into the database

				return "SUCCESS";
			} else {
				System.out.println("Something went wrong...");
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return test;

	}

}
