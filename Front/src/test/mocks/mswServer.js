import { setupServer } from 'msw/node';
import alertHandler from "./handlers/alertHandler.js";

export const mswServer = setupServer(...alertHandler);