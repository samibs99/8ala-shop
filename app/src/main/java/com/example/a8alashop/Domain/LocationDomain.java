package com.example.a8alashop.Domain;

/**
 * Représente un emplacement (location) dans l'application.
 * Cela peut correspondre à un magasin physique, un entrepôt, une ville, etc.
 */
public class LocationDomain {

  // Identifiant unique de l'emplacement
  private int id;

  // Nom ou libellé de l'emplacement (ex : "Tunis", "Entrepôt Central", etc.)
  private String loc;

  /**
   * Constructeur vide, nécessaire pour la désérialisation (par exemple depuis Firebase ou JSON).
   */
  public LocationDomain() {
  }

  /**
   * Redéfinition de la méthode toString(), utilisée automatiquement quand l'objet est affiché dans une liste
   * (comme un Spinner ou un Adapter). Cela permet d’afficher directement le nom de l’emplacement.
   */
  @Override
  public String toString() {
    return loc;
  }

  // Getter pour l'identifiant
  public int getId() {
    return id;
  }

  // Setter pour l'identifiant
  public void setId(int id) {
    this.id = id;
  }

  // Getter pour le nom de l'emplacement
  public String getLoc() {
    return loc;
  }

  // Setter pour le nom de l'emplacement
  public void setLoc(String loc) {
    this.loc = loc;
  }
}
