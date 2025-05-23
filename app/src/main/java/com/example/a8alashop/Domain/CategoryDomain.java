package com.example.a8alashop.Domain;

/**
 * Classe représentant une catégorie de produits dans l'application e-commerce.
 * Elle contient les informations nécessaires pour afficher et identifier une catégorie.
 */
public class CategoryDomain {

  // Chemin de l'image associée à la catégorie (peut être une URL ou un nom de fichier dans les ressources).
  private String ImagePath;

  // Nom de la catégorie (ex. : "Électronique", "Vêtements", etc.)
  private String Name;

  // Identifiant unique de la catégorie (utile pour la base de données ou la logique métier).
  private int Id;

  /**
   * Constructeur vide requis pour certaines opérations comme la désérialisation avec Firebase ou Gson.
   */
  public CategoryDomain() {
  }

  /**
   * Redéfinition de la méthode toString() pour retourner le nom de la catégorie.
   * Cela est utile lorsqu'on affiche des objets de cette classe dans une liste (Spinner, Adapter, etc.).
   */
  @Override
  public String toString() {
    return Name;
  }

  // Getter pour le chemin de l'image
  public String getImagePath() {
    return ImagePath;
  }

  // Setter pour le chemin de l'image
  public void setImagePath(String imagePath) {
    ImagePath = imagePath;
  }

  // Getter pour le nom de la catégorie
  public String getName() {
    return Name;
  }

  // Setter pour le nom de la catégorie
  public void setName(String name) {
    Name = name;
  }

  // Getter pour l'identifiant de la catégorie
  public int getId() {
    return Id;
  }

  // Setter pour l'identifiant de la catégorie
  public void setId(int id) {
    Id = id;
  }
}
