DELETE FROM saving_goal_event;
ALTER TABLE saving_goal_event ADD COLUMN fk_saving_goal INTEGER DEFAULT -1 NOT NULL;
