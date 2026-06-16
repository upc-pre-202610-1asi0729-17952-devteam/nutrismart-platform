package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ConfirmPlateScanCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ScanMenuCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ScanPlateCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateItemResult;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.RankedMenuResult;
import com.devteam.nutrismart.platform.shared.application.result.Result;

import java.util.List;

/**
 * Servicio de comandos para el escaneo inteligente de platos y menús mediante visión artificial.
 * <p>
 * Orquesta el flujo de reconocimiento de imágenes, enriquecimiento nutricional y registro
 * automático de comidas, coordinando los puertos de salida de reconocimiento de imágenes,
 * clasificación de menús y gestión de registros de comidas, dentro de la arquitectura
 * hexagonal del módulo de seguimiento nutricional.
 * </p>
 */
public interface SmartScanCommandService {

    /**
     * Procesa el comando de escaneo de un plato a partir de una imagen en Base64.
     * <p>
     * Identifica los alimentos presentes en el plato, los coteja con el catálogo local
     * y devuelve una lista de resultados con información nutricional estimada por porción.
     * </p>
     *
     * @param command comando que contiene la imagen codificada en Base64 del plato
     * @return un {@code Result} con la lista de {@code PlateItemResult} identificados en caso de éxito,
     *         o un {@code SmartScanCommandFailure} que describe el motivo del fallo
     */
    Result<List<PlateItemResult>, SmartScanCommandFailure> handlePlateScan(ScanPlateCommand command);

    /**
     * Procesa el comando de escaneo de un menú a partir de una imagen en Base64.
     * <p>
     * Extrae los platos del menú, los clasifica según el perfil nutricional del usuario
     * y devuelve una lista ordenada por compatibilidad con sus restricciones alimentarias.
     * </p>
     *
     * @param command comando que contiene la imagen del menú en Base64 y el identificador del usuario
     * @return un {@code Result} con la lista de {@code RankedMenuResult} clasificados en caso de éxito,
     *         o un {@code SmartScanCommandFailure} que describe el motivo del fallo
     */
    Result<List<RankedMenuResult>, SmartScanCommandFailure> handleMenuScan(ScanMenuCommand command);

    /**
     * Procesa el comando de confirmación de los ítems detectados en un escaneo de plato.
     * <p>
     * Crea los registros de comida ({@code MealRecord}) correspondientes a los ítems
     * confirmados por el usuario, persistiendo el consumo nutricional asociado.
     * </p>
     *
     * @param command comando que contiene la lista de ítems confirmados y el tipo de comida
     * @return un {@code Result} con el número de registros de comida creados en caso de éxito,
     *         o un {@code SmartScanCommandFailure} que describe el motivo del fallo
     */
    Result<Integer, SmartScanCommandFailure> handlePlateScanConfirm(ConfirmPlateScanCommand command);
}
