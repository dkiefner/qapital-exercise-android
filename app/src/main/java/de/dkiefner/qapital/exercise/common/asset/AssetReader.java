package de.dkiefner.qapital.exercise.common.asset;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetReader {

	private AssetManager assetManager;

	public AssetReader(Context context) {
		this.assetManager = context.getAssets();
	}

	public String read(String file) throws ReadAssetException {
		StringBuilder content = new StringBuilder();
		try {
			readInto(file, content);
		} catch (IOException e) {
			throw new ReadAssetException(e);
		}

		if (content.length() == 0) {
			throw new ReadAssetException("File " + file + " has no content.");
		}

		return content.toString();
	}

	private void readInto(String file, StringBuilder content) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open(file);
			BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = r.readLine()) != null) {
				content.append(line);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
