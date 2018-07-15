package pl.meowczki.DietSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.optimization.linear.SimplexSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.meowczki.entity.FoodData;
import pl.meowczki.repository.FoodRepo;

@Component
public class Lp {
	@Autowired
	FoodRepo foodRepo;
	public List<FoodData> getItems(String name) {
		return foodRepo.findByNameStartingWith(name);
	}
	public List<FoodData> addedItems=new ArrayList<>();
	
	public List<String> getNames() {
		List<String> list =addedItems.stream().map(FoodData::getName).collect(Collectors.toList());
		return list;
	}
		public double[] getKcals() {
		List<Double> list =addedItems.stream().map(FoodData::getKcals).collect(Collectors.toList());


		double[] kcals = list.stream().mapToDouble(d -> d).toArray();
		return kcals;
	}

	public double[] getProt() {
		List<Double> list =addedItems.stream().map(FoodData::getProt).collect(Collectors.toList());
		double[] prot = list.stream().mapToDouble(d -> d).toArray();
		return prot;
	}

	public double[] getFat() {
		List<Double> list =addedItems.stream().map(FoodData::getFat).collect(Collectors.toList());
		double[] fat = list.stream().mapToDouble(d -> d).toArray();
		return fat;
	}

	public double[] getCarb() {
		List<Double> list =addedItems.stream().map(FoodData::getCarb).collect(Collectors.toList());
		double[] carb = list.stream().mapToDouble(d -> d).toArray();

		return carb;
	}

	public double[] getFib() {
		List<Double> list =addedItems.stream().map(FoodData::getFib).collect(Collectors.toList());
		double[] fib = list.stream().mapToDouble(d -> d).toArray();
		return fib;
	}

	public double[] getFoodItemsWeight() {
		double[] foodItemsWeight = new double[addedItems.size()];
		Arrays.fill(foodItemsWeight, 1);
		return foodItemsWeight;
	}

	@SuppressWarnings("deprecation")
	public List<FoodData> getDiet(double kcalLimit) throws OptimizationException {
		LinearObjectiveFunction f = new LinearObjectiveFunction(getFoodItemsWeight(), 0);
		Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		constraints.add(new LinearConstraint(getKcals(), Relationship.GEQ, kcalLimit));
		constraints.add(new LinearConstraint(getProt(), Relationship.GEQ, kcalLimit * 0.4 / 4));
		constraints.add(new LinearConstraint(getFat(), Relationship.LEQ, kcalLimit * 0.2 / 9));
		constraints.add(new LinearConstraint(getCarb(), Relationship.EQ, kcalLimit * 0.4 / 4));
		constraints.add(new LinearConstraint(getFib(), Relationship.GEQ, 20));
		double[] xConstraint = new double[getKcals().length];

		for (int i = 0; i < getKcals().length; i++) {
			xConstraint[i] = 1;
			constraints.add(new LinearConstraint(xConstraint, Relationship.GEQ, 0));
			xConstraint[i] = 0;
		}
		RealPointValuePair solution = new SimplexSolver().optimize(f, constraints, GoalType.MINIMIZE, true);
		List<Double> result = DoubleStream.of(solution.getPoint()).boxed().collect(Collectors.toList());
		List<FoodData> foodDatas = new ArrayList<>();
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i) != 0) {
				FoodData item = foodRepo.getOne((long) (i + 1));
				double roundOff = Math.round(result.get(i) * 100.0) / 100.0;

				item.setWeight(roundOff);
				foodDatas.add(item);
			}

		}
		return foodDatas;

	}

}
