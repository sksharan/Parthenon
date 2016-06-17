CREATE TABLE item_stack (
  id      SERIAL PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  amount  INTEGER NOT NULL,
  type    VARCHAR(255) NOT NULL
);

CREATE TABLE player_item_stack (
  player_id      BIGINT UNSIGNED NOT NULL,
  item_stack_id  BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY (player_id) REFERENCES player(id),
  FOREIGN KEY (item_stack_id) REFERENCES item_stack(id)
);
