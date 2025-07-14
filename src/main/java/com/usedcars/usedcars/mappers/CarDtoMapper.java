package com.usedcars.usedcars.mappers;

import com.usedcars.usedcars.dtos.CarDto;
import com.usedcars.usedcars.dtos.CarRequestDto;
import com.usedcars.usedcars.dtos.CarShortDto;
import com.usedcars.usedcars.model.Car;
import com.usedcars.usedcars.model.Picture;
import com.usedcars.usedcars.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface CarDtoMapper {

    @Mappings({
            @Mapping(target = "pictures", source = "pictures", qualifiedByName = "stringToPath")})
    Car dtoToCar(CarRequestDto carRequestDto);


    @Mappings({
            @Mapping(target = "pictures", source = "pictures", qualifiedByName = "pathToString"),
            @Mapping(target="userId", source="user", qualifiedByName = "userToId"),
            @Mapping(target="userName", source="user", qualifiedByName = "userToUserName")})
    CarDto carToDto(Car car);

    @Named("stringToPath")
    default Set<Picture> stringToPath(Set<String> pictures){
        if (pictures == null) return null;
        return pictures.stream()
                .map(picture -> {
                    byte[] imageBytes;
                    imageBytes = Base64.getDecoder().decode(picture.substring(23));
                    String name = "images/" + System.currentTimeMillis() + ".jpg";
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(name);
                        fos.write(imageBytes);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return new Picture().builder()
                            .picture(name)
                            .build();
                })
                .collect(Collectors.toSet());

    }

    @Named("pathToString")
    default Set<String> pathToString(Set<Picture> pictures){
        return pictures == null ? null : pictures.stream()
                .map(picture -> {
                    try {
                        return Base64
                                .getEncoder()
                                .encodeToString(Files.readAllBytes(Paths.get(picture.getPicture())));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

    }

    @Named("userToId")
    default Long userToId(User user){
        return user != null ? user.getId() : null;
    }

    @Named("userToUserName")
    default String userToUserName(User user){
        return user != null ? user.getFirstname() + " " + user.getLastname() : null;
    }

    @Mappings({@Mapping(source="pictures", target="picture", qualifiedByName = "picturesToPicture")})
    CarShortDto carToShortDto(Car car);

    List<CarShortDto> carToShortDto(List<Car> car);

    @Named("picturesToPicture")
    default String picturesToPicture(Set<Picture> pictures) throws IOException {
        Optional<Picture> picture = pictures.stream().findFirst();
        return picture.isEmpty() ? null : Base64
                    .getEncoder()
                    .encodeToString(Files.readAllBytes(Paths.get(picture.get().getPicture())));
    }
}
