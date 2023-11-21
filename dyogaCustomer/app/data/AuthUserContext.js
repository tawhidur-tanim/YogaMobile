import { createContext } from 'react';
import {auth} from "../firebase";

export const AuthUserContext = createContext(auth.currentUser ?? {});