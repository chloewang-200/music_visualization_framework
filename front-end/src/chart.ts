interface ChartData {
  setDataPlugin: boolean;
  setVisualPlugin: boolean;
  chartJSON: string | null;
  dataPlugin: string | null;
  visualPlugin: string | null;
  dataPlugins: Plugin[];
  visualPlugins: Plugin[];
  // textFieldValue: string | null;
  userInput: string | null;
  instruction: string;
  userSubmitted: boolean;
  songNum: number;
  invalidSongs: String[];
}

interface Plugin {
  name: string;
}

export type { ChartData }