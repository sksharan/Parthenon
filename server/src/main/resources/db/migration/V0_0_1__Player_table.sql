CREATE TABLE player(
  id          SERIAL PRIMARY KEY,
  name        VARCHAR(255) NOT NULL UNIQUE,
  health      DECIMAL NOT NULL,
  max_health  DECIMAL NOT NULL,
  exp_level   INTEGER NOT NULL,
  food_level  INTEGER NOT NULL
);
