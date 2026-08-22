import { View, StyleSheet } from 'react-native';
import { PageControl } from 'react-native-page-control';
import { useState } from 'react';

const INACTIVE_DOT_COLOR = '#FFFFFF33';

const ACTIVE_DOT_COLOR = '#FFDD00';

export default function App() {
  const [currentPage, setCurrentPage] = useState(2);

  return (
    <View style={styles.container}>
      <PageControl
        style={styles.box}
        numberOfPages={10}
        currentPage={currentPage}
        pageIndicatorTintColor={INACTIVE_DOT_COLOR}
        currentPageIndicatorTintColor={ACTIVE_DOT_COLOR}
        hidesForSinglePage
        onPageChange={undefined}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#474747',
  },
  box: {
    height: 26,
  },
});
