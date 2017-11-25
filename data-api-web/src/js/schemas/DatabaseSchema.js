import { schema } from 'normalizr';

export const database = new schema.Entity('databases', {}, { idAttribute: 'id' });
export const arrayOfDatabase = [database];