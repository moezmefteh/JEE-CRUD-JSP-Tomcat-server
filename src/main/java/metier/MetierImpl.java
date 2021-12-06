package metier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MetierImpl implements ImetierCatalogue {
	@Override
	public List<Produit> getProduitsParMotCle(String mc) {
		List<Produit> prods = new ArrayList<Produit>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from PRODUITS where NOM_PRODUIT LIKE ?");
			ps.setString(1,"%"+mc+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Produit p = new Produit();
				p.setIdProduit(rs.getLong("ID_PRODUIT"));
				p.setNomProduit(rs.getString("NOM_PRODUIT"));
				p.setPrix(rs.getDouble("PRIX"));
				prods.add(p);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
return prods;
	}
	@Override
	public Produit addProduit(Produit p) {
			Connection con=SingletonConnection.getConnection();
			try {
				PreparedStatement ps=con.prepareStatement("insert into produits (NOM_PRODUIT,PRIX) values(?,?)");
				ps.setString(1,p.getNomProduit());
				ps.setDouble(2, p.getPrix());
				ps.executeUpdate();
				PreparedStatement ps2= con.prepareStatement
						("SELECT MAX(ID_PRODUIT) as MAX_ID FROM PRODUITS");
						ResultSet rs =ps2.executeQuery();
						if (rs.next()) {
						p.setIdProduit(rs.getLong("MAX_ID"));}
						ps2.close();
				ps.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return p;
		}
	@Override
	public Produit getProduit(Long id) {
		Connection conn=SingletonConnection.getConnection();
		Produit p = new Produit();
		try {
			PreparedStatement ps= conn.prepareStatement("select * from PRODUITS where ID_PRODUIT = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
			p.setIdProduit(rs.getLong("ID_PRODUIT"));
			p.setNomProduit(rs.getString("NOM_PRODUIT"));
			p.setPrix(rs.getDouble("PRIX"));
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	@Override
	public Produit updateProduit(Produit p) {
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("UPDATE PRODUITS SET NOM_PRODUIT=?,PRIX=? WHERE ID_PRODUIT=?");
			ps.setString(1, p.getNomProduit());
			ps.setDouble(2, p.getPrix());
			ps.setLong(3, p.getIdProduit());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	@Override
	public void deleteProduit(Long id) {
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps= conn.prepareStatement("DELETE FROM PRODUITS WHERE ID_PRODUIT = ?");
			ps.setLong(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
	


