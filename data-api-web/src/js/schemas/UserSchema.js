import { schema } from 'normalizr';

export const user = new schema.Entity('users', {}, { idAttribute: 'id' });
export const arrayOfUser = [user];
