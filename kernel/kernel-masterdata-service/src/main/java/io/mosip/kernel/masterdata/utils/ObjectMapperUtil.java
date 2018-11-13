package io.mosip.kernel.masterdata.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.mosip.kernel.masterdata.dto.DeviceLangCodeDtypeDto;
import io.mosip.kernel.masterdata.dto.HolidayDto;
import io.mosip.kernel.masterdata.entity.Holiday;
import io.mosip.kernel.masterdata.entity.HolidayId;

@Component
public class ObjectMapperUtil {
	@Autowired
	private ModelMapper mapper;

	public <D, T> D map(final T entity, Class<D> outCLass) {
		return mapper.map(entity, outCLass);
	}

	public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream().map(entity -> mapper.map(entity, outCLass))
				.collect(Collectors.toList());
	}

	public List<HolidayDto> mapHolidays(List<Holiday> holidays) {
		Objects.requireNonNull(holidays);
		List<HolidayDto> holidayDtos = new ArrayList<>();
		for (Holiday holiday : holidays) {
			LocalDate date = holiday.getHolidayId().getHolidayDate();
			HolidayId holidayId = holiday.getHolidayId();
			HolidayDto dto = new HolidayDto();
			dto.setHolidayId(String.valueOf(holidayId.getId()));
			dto.setHolidayDate(String.valueOf(date));
			dto.setHolidayName(holiday.getHolidayName());
			dto.setLanguageCode(holidayId.getLangCode());
			dto.setHolidayYear(String.valueOf(date.getYear()));
			dto.setHolidayMonth(String.valueOf(date.getMonth().getValue()));
			dto.setHolidayDay(String.valueOf(date.getDayOfWeek().getValue()));

			holidayDtos.add(dto);
		}
		return holidayDtos;
	}
	
	
	public List<DeviceLangCodeDtypeDto> mapDeviceDto(List<Object[]> objects) {
		
		List<DeviceLangCodeDtypeDto> deviceLangCodeDtypeDtoList = new ArrayList<>();
		for (Object[] arr : objects) {
			
			DeviceLangCodeDtypeDto deviceLangCodeDtypeDto = new DeviceLangCodeDtypeDto();
		
			deviceLangCodeDtypeDto.setId((String)arr[0]);
			deviceLangCodeDtypeDto.setName((String)arr[1]);
			deviceLangCodeDtypeDto.setMacAddress((String)arr[2]);
			deviceLangCodeDtypeDto.setSerialNum((String)arr[3]);
			deviceLangCodeDtypeDto.setIpAddress((String)arr[4]);
			deviceLangCodeDtypeDto.setDspecId((String)arr[5]);
			deviceLangCodeDtypeDto.setLangCode((String)arr[6]);
			deviceLangCodeDtypeDto.setActive((boolean)arr[7]);
			deviceLangCodeDtypeDto.setDeviceTypeCode((String)arr[8]);
			deviceLangCodeDtypeDtoList.add(deviceLangCodeDtypeDto);
			
		}
		return deviceLangCodeDtypeDtoList;
	}
}
