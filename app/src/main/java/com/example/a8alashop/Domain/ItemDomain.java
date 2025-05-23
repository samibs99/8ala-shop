package com.example.a8alashop.Domain;

import java.io.Serializable;

/**
 * Représente un article (produit) dans l'application e-commerce.
 * Cette classe implémente Serializable pour permettre le passage d'objets entre activités (Intent).
 */
public class ItemDomain implements Serializable {

  // Titre ou nom de l'article (ex : "Chaussures de randonnée")
  private String Title;

  // Chemin de l'image représentant l'article (URL ou nom de ressource)
  private String ImagePath;

  // Description détaillée de l'article
  private String Description;

  // Prix de l'article
  private double Price;

  // Évaluation moyenne de l'article (ex : 4.5 étoiles)
  private double Star;

  // Identifiant unique de l'article (utile pour les bases de données ou la logique métier)
  private int Id;

  // Identifiant de la catégorie à laquelle appartient l'article
  private int CategoryId;

  // Identifiant de l'emplacement (peut représenter un magasin, une région, un entrepôt, etc.)
  private int LocationId;

  /**
   * Constructeur vide nécessaire pour la désérialisation (ex : Firebase, JSON).
   */
  public ItemDomain() {
  }

  // Getter et Setter pour le titre de l'article
  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  // Getter et Setter pour le chemin de l'image
  public String getImagePath() {
    return ImagePath;
  }

  public void setImagePath(String imagePath) {
    ImagePath = imagePath;
  }

  // Getter et Setter pour la description
  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  // Getter et Setter pour le prix
  public double getPrice() {
    return Price;
  }

  public void setPrice(double price) {
    Price = price;
  }

  // Getter et Setter pour les étoiles (note)
  public double getStar() {
    return Star;
  }

  public void setStar(double star) {
    Star = star;
  }

  // Getter et Setter pour l'identifiant
  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  // Getter et Setter pour l'identifiant de catégorie
  public int getCategoryId() {
    return CategoryId;
  }

  public void setCategoryId(int categoryId) {
    CategoryId = categoryId;
  }

  // Getter et Setter pour l'identifiant de localisation
  public int getLocationId() {
    return LocationId;
  }

  public void setLocationId(int locationId) {
    LocationId = locationId;
  }
}
