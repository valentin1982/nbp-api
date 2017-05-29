package pl.nbp.api.beans;

import pl.nbp.api.domain.Rate;
import pl.nbp.api.util.date.DateHelperUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chart {

	private List<String> labels;

	private List<Map<String, Object>> data;

	/**
	 *
	 *
	 * @param exchangeRates
	 */
	public Chart(ExchangeRates exchangeRates) {
		if (exchangeRates != null) {
			List<ExchangeRates> list = new ArrayList<>();
			list.add(exchangeRates);
			build(list);
		}
	}
	/**
	 *
	 * @param exchangeRates
	 */
	private void build(List<ExchangeRates> exchangeRates) {
		this.labels = null;
		this.data = new ArrayList<>();
		for (ExchangeRates rates: exchangeRates) {
			if (labels == null) {
				labels = rates.getRates().stream().map(rating -> rating.getEffectivedate().format(DateHelperUtil.TIME_FORMATTER))
						.collect(Collectors.toList());
			}
			Map<String,Object> map = new HashMap<>();
			map.put("currency", rates.getCurrency().getCode());
			map.put("rates",
					rates.getRates().stream().map(Rate::getMid).collect(Collectors.toList())
			);
			data.add(map);
		}
	}

	public List<String> getLabels() {
		return labels;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}
	
}
