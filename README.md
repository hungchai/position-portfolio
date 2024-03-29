# Getting Started

### Preqrequisites
- Java 17

### Running the application
./gradlew build
./gradlew bootRun

### environment Variables
- path of the sample position csv file
  - `positioncsv.path=src/main/resources/sample_position.csv`

- dt for the mock market price provider
  - `mockMarketPriceProvider.gbm.dt=200`

- initialDate for the mock market price provider
  - `mockMarketPriceProvider.gbm.initialDate=2020-09-01T00:00:00Z`