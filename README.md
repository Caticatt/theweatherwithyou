# YourWeather 🌤️

YourWeather es una aplicación de clima moderna desarrollada en Kotlin para Android que te permite conocer las condiciones meteorológicas actuales y el pronóstico por horas.

## Características 🌟

- **Clima Actual**: Visualiza la temperatura, condiciones climáticas, velocidad del viento, precipitación y presión atmosférica de tu ubicación actual.
- **Pronóstico por Horas**: Consulta el pronóstico detallado hora por hora.
- **Diseño Moderno**: Interfaz de usuario limpia y moderna utilizando Material Design 3.
- **Actualización en Tiempo Real**: Datos meteorológicos actualizados automáticamente.

## Tecnologías Utilizadas 🛠️

- Kotlin
- Android Jetpack
- Material Design 3
- Clean Architecture
- MVVM Pattern
- Coroutines & Flow
- ViewBinding
- Navigation Component

## Arquitectura 🏗️

La aplicación sigue los principios de Clean Architecture y el patrón MVI:

- **UI Layer**: 
  - Activities/Fragments
  - ViewModels
  - Estados UI
- **Domain Layer**:
  - Casos de uso
  - Modelos de dominio
  - Repositorios
- **Data Layer**:
  - Repositorios de implementación
  - Fuentes de datos
  - Modelos de datos

## Requisitos 📋

- Android 8.0 (API level 26) o superior
- Permiso de ubicación para obtener el clima local

## Instalación 📥

1. Clona el repositorio:
```bash
git clone https://github.com/tu-usuario/yourweather.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza el proyecto con los archivos Gradle

4. Ejecuta la aplicación en un emulador o dispositivo físico

## Uso 🚀

1. Concede permisos de ubicación cuando la aplicación los solicite
2. La pantalla principal mostrará el clima actual de tu ubicación
3. Toca "Ver tiempo durante el día" para ver el pronóstico por horas

## Contribuir 🤝

Las contribuciones son bienvenidas. Por favor, sigue estos pasos:

1. Haz un Fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia 📄

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para más detalles.

## Agradecimientos 🙏

- [API de Clima](https://www.weatherapi.com/) por proporcionar los datos meteorológicos
- [Material Design](https://material.io/) por los componentes de UI
- [Android Jetpack](https://developer.android.com/jetpack) por las herramientas de desarrollo
