package pl.meowczki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.meowczki.entity.FoodData;

public interface FoodRepo extends JpaRepository<FoodData, Long> {
	  @Query("select f.kcals from FoodData f")
	    List<Double> getKcals();
	  @Query("select f.prot from FoodData f")
	    List<Double> getProt();
	  @Query("select f.fat from FoodData f")
	    List<Double> getCarb();
	  @Query("select f.fat from FoodData f")
	    List<Double> getFat();
	  @Query("select f.fat from FoodData f")
	    List<Double> getFib();
	  List<FoodData> findByNameStartingWith(String name);
	  FoodData findById(long id);



}
