import { client } from './ClientSchema';
import { user, arrayOfUser } from './UserSchema';
import { database, arrayOfDatabase } from './DatabaseSchema';

export default {
    client: client,
    user: user,
    users: arrayOfUser,
    database: database,
    databases: arrayOfDatabase
};