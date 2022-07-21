package objects.categories;

import java.util.List;

public class CategoriesListDTO {
    private List<String> categoriesList;

    public CategoriesListDTO(List<String> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public void print(){
        int i = 1;
        for(String category:categoriesList){
            System.out.println(i + ". " + category);
            i++;
        }
    }

    public List<String> getCategoriesList() {
        return categoriesList;
    }
}
