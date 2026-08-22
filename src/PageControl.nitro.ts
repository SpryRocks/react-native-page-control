import type {
  HybridView,
  HybridViewMethods,
  HybridViewProps,
} from 'react-native-nitro-modules';

export interface PageControlProps extends HybridViewProps {
  color: string;
}
export interface PageControlMethods extends HybridViewMethods {}

export type PageControl = HybridView<
  PageControlProps,
  PageControlMethods
>;
