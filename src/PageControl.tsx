import { getHostComponent } from 'react-native-nitro-modules';
const PageControlConfig = require('../nitrogen/generated/shared/json/PageControlConfig.json');
import type { PageControlMethods, PageControlProps } from './PageControl.nitro';

export const PageControl = getHostComponent<
  PageControlProps,
  PageControlMethods
>('PageControl', () => PageControlConfig);
