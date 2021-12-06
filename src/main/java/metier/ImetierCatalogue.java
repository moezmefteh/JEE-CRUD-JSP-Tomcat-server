package metier;
import java.util.List;
public interface ImetierCatalogue {
	public List<Produit> getProduitsParMotCle(String mc);
	public Produit addProduit(Produit p);
	Produit getProduit(Long id);
	Produit updateProduit(Produit p);
	void deleteProduit(Long id);
}
