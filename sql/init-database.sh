#!/bin/bash

sudo -u postgres psql -d family_tree < clear.sql
sudo -u postgres psql -d family_tree < create.sql
sudo -u postgres psql -d family_tree -c "GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO family_tree;"
sudo -u postgres psql -d family_tree -c "GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO family_tree;"

