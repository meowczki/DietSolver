package pl.meowczki.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.optimization.OptimizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.meowczki.DietSolver.Lp;
import pl.meowczki.entity.FoodData;
import pl.meowczki.repository.FoodRepo;

@SuppressWarnings("deprecation")
@Controller
public class FoodController {
	@Autowired
	FoodRepo foodRepo;
	@Autowired
	Lp lp;

	@ModelAttribute("addedItems")
	public List<FoodData> getAddedItems() {
		return lp.addedItems;
	}

	@ModelAttribute("addedItemsNames")
	public List<String> getAddedItemsNames() {
		return lp.getNames();
	}

	@RequestMapping(value = "/solveDiet", method = RequestMethod.GET)
	public String getDiet(Model model) throws OptimizationException {
		try {
			List<FoodData> solutions = lp.getDiet(2000);
			model.addAttribute("solutions", solutions);
			return "table";

		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@GetMapping("/")
	public String autocomplete(Model model) {
		model.addAttribute("title", "Design your diet");

		return "autocomplete";
	}

	@RequestMapping(value = "/suggestion", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<FoodData> autocompleteSuggestions(@RequestParam("searchstr") String searchstr) {
		List<FoodData> suggestions = new ArrayList<>();
		suggestions = foodRepo.findByNameStartingWith(searchstr);
		return suggestions;
	}

	@RequestMapping(value = "/addItem/{id}", method = RequestMethod.POST)
	public void addItem(@PathVariable String id) {
		long idLong = Long.parseLong(id);
		lp.addedItems.add(foodRepo.findById(idLong));
		System.out.println(lp.addedItems.get(0));

	}
}
