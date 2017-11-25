import { schema } from 'normalizr';

export const client = new schema.Entity('clients', {}, { idAttribute: 'id' });