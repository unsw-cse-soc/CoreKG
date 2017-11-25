export const getClients = (state) => state.auth.get('clients');

export const getUsers = (state) => state.auth.get('users');

export const getResponses = (state) => state.auth.get('responses');

export const getAccessToken = (state) => state.auth.get('token').accessToken;